package zero.springboot.study.redission.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zero.springboot.study.redission.dto.SignDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author magebte
 */
@Slf4j
@Service
public class BitmapSignService {

    @Autowired
    private RedissonClient redissonClient;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 签到
     *
     * @param userId
     * @param date
     * @return
     */
    public SignDTO doSign(Integer userId, LocalDate date) {
        //获取日期对应的天，从 0 开始，所以 - 1 作为 offset
        int offset = date.getDayOfMonth() - 1;
        // 构建 key
        String signKey = buildSignKey(userId, date);

        // 指定日期是否签到
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        boolean isSigned = bitSet.get(offset);

        // 已经签到，直接返回签到数据。
        if (isSigned) {
            return getSignDetailInfo(userId, date);
        }
        // 签到,  SETBIT uid:sign:89757:yyyyMM offset 1
        bitSet.set(offset);

        // 设置超时，超时的日期是 date 所在月份的下一个月的 1 号，假设当前 date = 2023-07-26，那么失效时间就是 2023-08-01 00:00:00
        Instant expireDateInstant = date.plusMonths(1L).withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        // 只在 key 没有设置过超时时间才执行
        bitSet.expireIfNotSet(expireDateInstant);

        return getSignDetailInfo(userId, date);
    }

    /**
     * 获取 date 月份签到详情
     * @param userId
     * @param date
     * @return
     */
    public SignDTO getSignDetailInfo(Integer userId, LocalDate date) {
        List<SignDTO.SignDetailDTO> signDateList = getSignDateList(userId, date);

        LocalDate firstSignDate = getFirstSignDate(signDateList);

        return new SignDTO()
                .setContinuous(getContinuousSignCount(signDateList))
                .setCount(getSumSignCount(userId, date))
                .setFirstSignDate(firstSignDate)
                .setSignDetailList(signDateList);
    }

    /**
     * 连续签到次数
     * @param signDateList
     * @return
     */
    private int getContinuousSignCount(List<SignDTO.SignDetailDTO> signDateList) {
        int maxContinuous = 0;

        if (CollectionUtils.isEmpty(signDateList)) {
            return maxContinuous;
        }

        // 连续签到计数器
        int currentContinuous = 0;
        for (SignDTO.SignDetailDTO signDetailDTO : signDateList) {
            if (signDetailDTO.getSigned()) {
                currentContinuous++;
                maxContinuous = Math.max(currentContinuous, maxContinuous);
            } else {
                currentContinuous = 0;
            }
        }

        return maxContinuous;
    }

    /**
     * 获取 date 签到列表数据
     * @param userId
     * @param date
     * @return
     */
    private List<SignDTO.SignDetailDTO> getSignDateList(Integer userId, LocalDate date) {
        String signKey = this.buildSignKey(userId, date);

        RBitSet bitSet = redissonClient.getBitSet(signKey);

        // e.g. BITFIELD uid:sign:{userId}:{yyyyMM} GET u{size} 0
        // 当前月份的天数作为 size 参数
        int dayOfMonth = date.lengthOfMonth();
        long signed = bitSet.getUnsigned(dayOfMonth, 0);

        List<SignDTO.SignDetailDTO> signDetailList = new ArrayList<>();

        // 从低位到高位进行遍历，为 0 表示未签到，为 1 表示已签到
        for(int i = dayOfMonth; i > 0; i--) {
            LocalDate localDate = date.withDayOfMonth(i);

            // 右移再左移，如果不等于自己说明最低位是 1，表示已签到
            boolean flag = signed >> 1 << 1 != signed;

            // 如果已签到，添加标记
            SignDTO.SignDetailDTO signDetailDTO = new SignDTO.SignDetailDTO()
                    .setDay(localDate)
                    .setSigned(flag);
            signDetailList.add(signDetailDTO);

            // 右移一位并重新赋值，相当于把最低位丢弃一位然后重新计算
            signed >>= 1;
        }

        return signDetailList;
    }

    /**
     * 统计总签到次数
     *
     * @param userId
     * @param date
     * @return
     */
    private Long getSumSignCount(Integer userId, LocalDate date) {
        String signKey = this.buildSignKey(userId, date);
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        // BITCOUNT uid:sign:89757:202307
        return bitSet.cardinality();
    }

    /**
     * Set all bits to zero
     *
     * @param userId
     * @param date
     */
    public void clear(Integer userId, LocalDate date) {
        String signKey = this.buildSignKey(userId, date);
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        bitSet.clear();
    }

    /**
     * Deletes the object
     *
     * @param userId
     * @param date
     * @return
     */
    public boolean delete(Integer userId, LocalDate date) {
        String signKey = this.buildSignKey(userId, date);
        RBitSet bitSet = redissonClient.getBitSet(signKey);
        return bitSet.delete();
    }

    /**
     * 构建 Redis key - uid:sign:{userId}:{yyyyMM}
     *
     * @param userId
     * @param date
     * @return
     */
    private String buildSignKey(Integer userId, LocalDate date) {
        return String.format("uid:sign:%d:%s", userId, date.format(DATE_TIME_FORMATTER));
    }

    /**
     * 获取第一次签到日期
     *
     * @param signDateList
     * @return
     */
    private LocalDate getFirstSignDate(List<SignDTO.SignDetailDTO> signDateList) {
        return signDateList.stream()
                .filter(SignDTO.SignDetailDTO::getSigned)
                .map(SignDTO.SignDetailDTO::getDay)
                .sorted()
                .findFirst()
                .orElse(null);
    }

}
