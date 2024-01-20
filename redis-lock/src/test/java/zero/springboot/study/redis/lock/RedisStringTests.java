package zero.springboot.study.redis.lock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import zero.springboot.study.redis.lock.entity.User;

@SpringBootTest
class RedisStringTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString() {
        stringRedisTemplate.opsForValue().set("name", "虎哥");
        System.out.println(stringRedisTemplate.opsForValue().get("name"));


        // 获取string数据
        redisTemplate.opsForValue().set("公众号", "码哥字节");
        System.out.println(redisTemplate.opsForValue().get("公众号"));
    }

    @Test
    public void testSaveUser() {
        User user1 = new User();
        user1.setId(100L);
        user1.setUsername("码哥字节");


        redisTemplate.opsForValue().set("user:100", user1);
        User user = (User) redisTemplate.opsForValue().get("user:100");
        System.out.println("User = " + user);
    }
}