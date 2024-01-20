package zero.springboot.study.jedis.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zero.springboot.study.jedis.JedisApplication;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = JedisApplication.class)
public class JedisLockTest {

    @Autowired
    private JedisLockClient jedisLockClient;

    @Test
    public void testLock() throws InterruptedException {
        Lock lock = jedisLockClient.getLock("order:pay");
        try {
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!isLock) {
                log.warn("加锁失败");
                return;
            }
            TimeUnit.SECONDS.sleep(3);
            log.info("业务逻辑执行完成");
        } finally {
            lock.unlock();
        }


    }
}
