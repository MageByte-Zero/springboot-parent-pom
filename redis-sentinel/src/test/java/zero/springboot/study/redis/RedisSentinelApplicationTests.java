package zero.springboot.study.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redis.domain.Person;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisSentinelApplication.class)
public class RedisSentinelApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testObjectTemplate() {
        Person person = new Person();
        person.setId(1L);
        person.setUsername("李健青");
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("person", person);
        Person cache = (Person) opsForValue.get("person");
        System.out.println(cache.toString());

    }

    @Test
    public void testStringTemplate() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("str", "string测试");
        String str = opsForValue.get("str");
        System.out.println(str);
    }

    @Test
    public void testHash() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1L);
        data.put("username", "李健青");
        HashOperations<String, String, Map<String, Object>> opsForHash = redisTemplate.opsForHash();
        opsForHash.put("map", "mapPerson", data);
        Map<String, Object> cacheData = opsForHash.get("map", "mapPerson");
        Object username = cacheData.get("username");
        Assert.assertEquals(username, "李健青");
        System.out.println(cacheData + "," + username);
    }

}
