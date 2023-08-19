package zero.springboot.study.redission.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author magebte
 */
@Accessors(chain = true)
@Data
public class SignDTO implements Serializable {

    /**
     * 签到总数
     */
    private Long count;

    /**
     * 连续签到次数
     */
    private Integer continuous;

    /**
     * 首次签到时间
     */
    private LocalDate firstSignDate;

    /**
     * 签到详情
     */
    private List<SignDetailDTO> signDetailList;

    @Accessors(chain = true)
    @Data
    public static class SignDetailDTO implements Serializable {
        /**
         * 日期
         */
        private LocalDate day;

        /**
         * 是否签到，true-是，false-否
         */
        private Boolean signed;
    }
}
