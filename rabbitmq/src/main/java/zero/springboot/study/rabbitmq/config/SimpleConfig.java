/**
 * Project: springboot-parent-pom
 * File created at 2019/4/26 15:11
 */
package zero.springboot.study.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列直接投放
 * @author lijianqing
 * @version 1.0
 * @ClassName SimpleConfig
 * @date 2019/4/26 15:11
 */
@Configuration
public class SimpleConfig {
    @Bean
    public Queue simpleQueue() {
        return new Queue("simple");
    }
}
