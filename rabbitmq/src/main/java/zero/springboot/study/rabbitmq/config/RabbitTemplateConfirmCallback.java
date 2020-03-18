/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 12:55
 */
package zero.springboot.study.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 生产者 confirms：
 * 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
 *
 * @author lijianqing
 * @version 1.0
 * @ClassName RabbitTemplateConfirmCallback
 * @date 2019/4/23 12:55
 */
@Component
@Slf4j
public class RabbitTemplateConfirmCallback implements RabbitTemplate.ConfirmCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息唯一标识：{},确认结果：{},失败原因：{}", correlationData, ack, cause);
    }
}
