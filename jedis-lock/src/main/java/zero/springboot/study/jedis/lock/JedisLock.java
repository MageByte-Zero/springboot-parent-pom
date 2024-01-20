package zero.springboot.study.jedis.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Jedis 分布式锁
 */
public class JedisLock implements Lock {

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
    private final RedisTemplate<String, Object> redisTemplate;

    public JedisLock(String resourceName, RedisTemplate<String, Object> redisTemplate) {
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
        Long ttl = tryAcquire(waitTime, leaseTime, unit, threadId);
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
            ttl = tryAcquire(waitTime, leaseTime, unit, threadId);
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

    private Long tryAcquire(long waitTime, long leaseTime, TimeUnit unit, long threadId) {
        List<String> args = new ArrayList<>(2);
        args.add(0, String.valueOf(unit.toMillis(leaseTime)));
        args.add(1, getRequestId(threadId));

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript.lockScript(), Long.class);
        return redisTemplate.execute(redisScript, keys, args);
    }

    private String getRequestId(long threadId) {
        return id + ":" + threadId;
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {

    }

    @Override
    public void unlock() {
        long threadId = Thread.currentThread().getId();

        List<String> args = new ArrayList<>(2);
        args.add(0, String.valueOf(internalLockLeaseTime));
        args.add(1, getRequestId(threadId));

        // 执行 lua 脚本
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript.lockScript(), Long.class);
        Long opStatus = redisTemplate.execute(redisScript, keys, args);

        if (opStatus == null) {
            throw new IllegalMonitorStateException("attempt to unlock lock, not locked by current thread by node id: "
                    + id + " thread-id: " + threadId);
        }


    }

}
