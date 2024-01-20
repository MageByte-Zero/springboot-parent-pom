package zero.springboot.study.jedis.lock;

/**
 * Redis 分布式锁 lua 脚本
 * @author magebte
 */
public class LuaScript {

    private LuaScript() {

    }

    public static String lockScript() {
        return "if ((redis.call('exists', KEYS[1]) == 0) " +
                "or (redis.call('hexists', KEYS[1], ARGV[2]) == 1)) then " +
                "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return nil; " +
                "end; " +
                "return redis.call('pttl', KEYS[1]);";
    }

    public static String unlockScript() {
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
