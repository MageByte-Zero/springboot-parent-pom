/**
 * Project: springboot-parent-pom
 * File created at 2019/4/17 11:14
 */
package zero.springboot.study.redis.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redisRedisTemplate配置，官方默认配置了Spring Boot 自动帮我们在容器中生成了一个RedisTemplate和一个StringRedisTemplate。
 * 但是，这个RedisTemplate的泛型是<Object,Object>。这样在写代码就很不方便，要写好多类型转换的代码。详情请见RedisAutoConfiguration
 * 并且设置这个RedisTemplate在数据存在Redis时key及value的序列化方式(默认使用的JdkSerializationRedisSerializer 这样的会导致我
 * 们通过redis desktop manager显示的我们key跟value的时候显示不是正常字符)。所以我们重写一个替换掉官方的
 *
 * @author lijianqing
 * @version 1.0
 * @ClassName RedisConfig
 * @date 2019/4/17 11:14
 * @see RedisAutoConfiguration
 */
@Configuration
public class RedisConfig {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
