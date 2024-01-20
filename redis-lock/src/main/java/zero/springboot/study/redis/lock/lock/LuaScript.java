package zero.springboot.study.redis.lock.lock;

/**
 * Redis 分布式锁 lua 脚本
 *
 * @author magebte
 */
public class LuaScript {

    private LuaScript() {

    }

    /**
     * 分布式锁加锁脚本
     *
     * @return 当且仅当返回 `1`才表示加锁成功.
     */
    public static String unlockScript() {
        return "if (redis.call('exists', KEYS[1]) == 0) then " +
                "return nil;" +
                "end; "+
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                "   return redis.call('del',KEYS[1]);" +
                "else" +
                "   return 0;" +
                "end;";
    }

    /**
     * 可重入分布式锁加锁脚本
     *
     * @return 当且仅当返回 `nil`才表示加锁成功；返回锁剩余过期时间是让客户端感知锁是否成功。
     */
    public static String reentrantLockScript() {
        return "if ((redis.call('exists', KEYS[1]) == 0) " +
                "or (redis.call('hexists', KEYS[1], ARGV[2]) == 1)) then " +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "return redis.call('pttl', KEYS[1]);";
    }

    /**
     * 可重入分布式锁解锁脚本
     *
     * @return 当且仅当返回 `nil`才表示解锁成功；
     */
    public static String reentrantUnlockScript() {
        return "if (redis.call('hexists', KEYS[1], ARGV[2]) == 0) then " +
                "return nil;" +
                "end; " +
                "local counter = redis.call('hincrby', KEYS[1], ARGV[2], -1); " +
                "if (counter > 0) then " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return 0; " +
                "else " +
                "redis.call('del', KEYS[1]); " +
                "return 1; " +
                "end; " +
                "return nil;";
    }
}
