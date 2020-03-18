/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 12:55
 */
package zero.springboot.study.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 消息失败返回，比如路由不到队列时触发回调
 * 如果发送到交换器成功，但是没有匹配的队列，就会触发这个回调
 *
 * @author lijianqing
 * @version 1.0
 * @ClassName RabbitTemplateReturnCallback
 * @date 2019/4/23 12:55
 */
@Component
@Slf4j
public class RabbitTemplateReturnCallback implements RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setMandatory(true);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息主体 message : " + message);
        log.info("消息主体 message : " + replyCode);
        log.info("描述：" + replyText);
        log.info("消息使用的交换器 exchange : " + exchange);
        log.info("消息使用的路由键 routing : " + routingKey);
    }
}
