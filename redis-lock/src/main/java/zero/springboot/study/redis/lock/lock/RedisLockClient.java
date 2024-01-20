package zero.springboot.study.redis.lock.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author magebte
 */
@Component
public class RedisLockClient {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public Lock getLock(String name) {
        return new DistributedLock(name, redisTemplate);
    }

    /**
     * 获取可重入分布式锁
     * @param name
     * @return
     */
    public Lock getReentrantLock(String name) {
        return new ReentrantDistributedLock(name, redisTemplate);
    }

}
