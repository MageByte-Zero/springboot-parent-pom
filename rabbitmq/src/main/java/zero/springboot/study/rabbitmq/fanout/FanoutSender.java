/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 16:32
 */
package zero.springboot.study.rabbitmq.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName FanoutSender
 * @date 2019/4/23 16:32
 */
@Component
@Slf4j
public class FanoutSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String context = "hi, fanout msg ";
        rabbitTemplate.convertAndSend("fanoutExchange", null, context);
    }

}
