package zero.springboot.study.redission.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.dto.SignDTO;
import zero.springboot.study.redission.service.BitmapSignService;

import java.time.LocalDate;

@Slf4j
@SpringBootTest(classes = RedissionApplication.class)
public class BitmapSignServiceTest {

    @Autowired
    private BitmapSignService bitmapSignService;

    @Autowired
    private RedissonClient redissonClient;

    private final LocalDate firstSignDate = LocalDate.of(2023, 8, 2);
    private final LocalDate secondSignDate = LocalDate.of(2023, 8, 3);
    private final LocalDate threeSignDate = LocalDate.of(2023, 8, 28);
    int userId = 89757;

    @Test
    public void testDoSigned() {
        //e.g. SETBIT uid:sign:89757:202308 1 1
        SignDTO sign1DTO = bitmapSignService.doSign(userId, firstSignDate);

        //e.g. SETBIT uid:sign:89757:202308 2 1
        SignDTO sign2DTO = bitmapSignService.doSign(userId, secondSignDate);

        //e.g. SETBIT uid:sign:89757:202308 28 1
        SignDTO sign3DTO = bitmapSignService.doSign(userId, threeSignDate);

        boolean delete = bitmapSignService.delete(userId, firstSignDate);

    }

    @Test
    public void testGetSignDetailInfo() {
        SignDTO signDetailInfo = bitmapSignService.getSignDetailInfo(userId, threeSignDate);
    }

    @Test
    public void testClear() {
        bitmapSignService.clear(userId, threeSignDate);
    }


    @Test
    public void testUnsign() {
        RBitSet bitSet = redissonClient.getBitSet("mybitset");


        // Set some bits in the RBitSet (for example, a 32-bit signed integer)
        bitSet.set(0, true);
        bitSet.set(3, true);
        bitSet.set(5, true);
        bitSet.set(6, true);
        // ...

        // Get the signed integer value from the RBitSet
        //  BITFIELD mybitset GET u32 0
        long unsignedValue = bitSet.getUnsigned(32, 0);
        // BITFIELD mybitset GET i32 0
        long signedValue = bitSet.getSigned(32, 0);

        System.out.println("unSigned Value: " + unsignedValue);
        System.out.println("Signed Value: " + signedValue);

    }

    @Test
    public void testLength() {
        // 获取 RBitSet 对象
        RBitSet bitSet = redissonClient.getBitSet("bitset_len");

        // 设置一些示例数据
        bitSet.set(1, true);
        bitSet.set(2, true);
        bitSet.set(28, true);

        // 获取位集合的长度
        long bitSetLength = bitSet.length();

        System.out.println("位集合的长度: " + bitSetLength);
    }

}
