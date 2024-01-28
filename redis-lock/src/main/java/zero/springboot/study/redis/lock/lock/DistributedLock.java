package zero.springboot.study.redis.lock.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis 分布式锁
 * @author magebte
 */
public class DistributedLock implements Lock {

    /**
     * 标识 id
     */
    private final String id = UUID.randomUUID().toString();

    /**
     * 资源名称
     */
    private final String resourceName;

    private final List<String> keys = new ArrayList<>(1);


    /**
     * redis 客户端
     */
    private final StringRedisTemplate redisTemplate;

    public DistributedLock(String resourceName, StringRedisTemplate redisTemplate) {
        this.resourceName = resourceName;
        this.redisTemplate = redisTemplate;
        keys.add(resourceName);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        long time = unit.toMillis(waitTime);
        long current = System.currentTimeMillis();
        long threadId = Thread.currentThread().getId();
        // lua 脚本获取锁
        Boolean isAcquire = tryAcquire(leaseTime, unit, threadId);
        // lock acquired
        if (Boolean.TRUE.equals(isAcquire)) {
            return true;
        }

        time -= System.currentTimeMillis() - current;
        // 等待时间用完，获取锁失败
        if (time <= 0) {
            return false;
        }
        // 自旋获取锁
        while (true) {
            long currentTime = System.currentTimeMillis();
            isAcquire = tryAcquire(leaseTime, unit, threadId);
            // lock acquired
            if (Boolean.TRUE.equals(isAcquire)) {
                return true;
            }

            time -= System.currentTimeMillis() - currentTime;
            if (time <= 0) {
                return false;
            }
        }
    }

    private Boolean tryAcquire(long leaseTime, TimeUnit unit, long threadId) {
        return redisTemplate.opsForValue().setIfAbsent(resourceName, getRequestId(threadId), leaseTime, unit);
    }

    private String getRequestId(long threadId) {
        return id + ":" + threadId;
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        long threadId = Thread.currentThread().getId();
        Boolean acquired;
        do {
            acquired = tryAcquire(leaseTime, unit, threadId);
        } while (Boolean.TRUE.equals(acquired));
    }

    @Override
    public void unlock() {
        long threadId = Thread.currentThread().getId();

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript.unlockScript(), Long.class);
        Long opStatus = redisTemplate.execute(redisScript, keys, getRequestId(threadId));

        if (opStatus == null) {
            throw new IllegalMonitorStateException("attempt to unlock lock, not locked by current thread by node id: "
                    + id + " thread-id: " + threadId);
        }


    }

}
