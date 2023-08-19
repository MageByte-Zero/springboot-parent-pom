package zero.springboot.study.redission.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author magebte
 */
@Configuration
public class RedisTemplateConfiguration {
    /**
     * RedisTemplate 序列化默认使用 JdkSerializationRedisSerializer 存储二进制字节码
     * 为了方便使用，自定义序列化策略
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 使用 Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        // JSON 对象处理
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        return getStringObjectRedisTemplate(redisConnectionFactory, keySerializer, jackson2JsonRedisSerializer);
    }

    private RedisTemplate<String, Object> getStringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory, StringRedisSerializer keySerializer, Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 为 String 类型 key/value 设置序列化器
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 为 Hash 类型 key/value 设置序列化器
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
