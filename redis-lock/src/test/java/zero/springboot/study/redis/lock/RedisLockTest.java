package zero.springboot.study.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zero.springboot.study.redis.lock.lock.Lock;
import zero.springboot.study.redis.lock.lock.RedisLockClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(classes = RedisApplication.class)
public class RedisLockTest {

    @Autowired
    private RedisLockClient redisLockClient;

    @Test
    public void testTryReentrantLockSuccess() throws InterruptedException {
        Lock lock = redisLockClient.getReentrantLock("order:pay");
        try {
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!isLock) {
                log.warn("加锁失败");
                return;
            }
            // 重复加锁
            reentrant(lock);

            log.info("业务逻辑执行完成");
        } finally {
            lock.unlock();
        }

    }

    private void reentrant(Lock lock) throws InterruptedException {
        try {
            boolean isLock = lock.tryLock(10, 30, TimeUnit.SECONDS);
            if (!isLock) {
                log.warn("加锁失败");
                return;
            }

            log.info("业务逻辑执行完成");
        } finally {
            lock.unlock();
        }
    }



    @Test
    public void testReentrantLockSuccess() throws InterruptedException {
        Lock lock = redisLockClient.getReentrantLock("order:pay");
        try {
            lock.lock(10, TimeUnit.SECONDS);

            TimeUnit.SECONDS.sleep(3);
            log.info("业务逻辑执行完成");
        } finally {
            lock.unlock();
        }

    }

    @Test
    public void testLockSuccess() throws InterruptedException {
        Lock lock = redisLockClient.getLock("order:pay");
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

    @Test
    public void testUnlockFail() throws InterruptedException {
        Lock lock = redisLockClient.getLock("order:pay");
        Assertions.assertThrows(IllegalMonitorStateException.class, lock::unlock);

    }


}
