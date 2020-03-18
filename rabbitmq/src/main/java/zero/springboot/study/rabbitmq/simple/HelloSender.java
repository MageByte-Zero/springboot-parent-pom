/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 11:22
 */
package zero.springboot.study.rabbitmq.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zero.springboot.study.rabbitmq.model.User;

import java.util.UUID;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName HelloSender
 * @date 2019/4/23 11:22
 */
@Component
@Slf4j
public class HelloSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        User user = new User();
        user.setName("青");
        user.setPass("111111");
        //发送消息到hello队列
        log.info("发送消息：{}", user);
        rabbitTemplate.convertAndSend("hello", user, new CorrelationData(UUID.randomUUID().toString()));

        String msg = "hello qing";
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend("simple", msg);
    }
}
