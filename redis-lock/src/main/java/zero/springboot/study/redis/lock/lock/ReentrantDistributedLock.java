package zero.springboot.study.redis.lock.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis 可重入分布式锁
 * @author magebte
 */
public class ReentrantDistributedLock implements Lock {

    /**
     * 锁超时时间，默认 30 秒
     */
    protected long internalLockLeaseTime = 30000;

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
     * Jedis 客户端
     */
    private final StringRedisTemplate redisTemplate;

    public ReentrantDistributedLock(String resourceName, StringRedisTemplate redisTemplate) {
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
        Long ttl = tryAcquire(leaseTime, unit, threadId);
        // lock acquired
        if (ttl == null) {
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
            ttl = tryAcquire(leaseTime, unit, threadId);
            // lock acquired
            if (ttl == null) {
                return true;
            }

            time -= System.currentTimeMillis() - currentTime;
            if (time <= 0) {
                return false;
            }
        }
    }

    private Long tryAcquire(long leaseTime, TimeUnit unit, long threadId) {
        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript.reentrantLockScript(), Long.class);
        return redisTemplate.execute(redisScript, keys, String.valueOf(unit.toMillis(leaseTime)), getRequestId(threadId));
    }

    private String getRequestId(long threadId) {
        return id + ":" + threadId;
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        long threadId = Thread.currentThread().getId();
        Long ttl = tryAcquire(leaseTime, unit, threadId);
        // lock acquired
        if (ttl == null) {
            return;
        }
        do {
            ttl = tryAcquire(leaseTime, unit, threadId);
            // lock acquired
        } while (ttl != null);
    }

    @Override
    public void unlock() {
        long threadId = Thread.currentThread().getId();

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript.reentrantUnlockScript(), Long.class);
        Long opStatus = redisTemplate.execute(redisScript, keys, String.valueOf(internalLockLeaseTime), getRequestId(threadId));

        if (opStatus == null) {
            throw new IllegalMonitorStateException("attempt to unlock lock, not locked by current thread by node id: "
                    + id + " thread-id: " + threadId);
        }


    }

}
