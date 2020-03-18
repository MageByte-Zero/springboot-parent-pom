/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 11:15
 */
package zero.springboot.study.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author lijianqing
 * @version 1.0
 * @ClassName DirectConfig
 * @date 2019/4/23 11:15
 */
@Configuration
public class DirectConfig {

    public static final String QUEUE_NAME = "direct_name";

    public static final String EXCHANGE = "zero-exchange";

    public static final String ROUTING_KEY = "routingKey";

    @Bean
    public Queue blueQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingBlue() {
        return BindingBuilder.bind(blueQueue()).to(defaultExchange()).with(ROUTING_KEY);
    }


}
