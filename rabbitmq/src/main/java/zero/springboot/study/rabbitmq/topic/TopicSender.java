/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 15:10
 */
package zero.springboot.study.rabbitmq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName TopicSender
 * @date 2019/4/23 15:10
 */
@Component
@Slf4j
public class TopicSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 匹配topic.message，两个队列都会收到
     */
    public void send1() {
        String context = "hi, i am message 1";
        log.info("主题发送 : {}" , context);
        rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    /**
     * 匹配topic.messages
     */
    public void send2() {
        String context = "hi, i am messages 2";
        log.info("主题发送 : {}" , context);
        rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }
}
