/**
 * Project: springboot-parent-pom
 * File created at 2019/4/23 11:42
 */
package zero.springboot.study.rabbitmq.direct;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import zero.springboot.study.rabbitmq.model.User;

import java.io.IOException;

/**
 * 监听hello队列
 *
 * @author lijianqing
 * @version 1.0
 * @ClassName HelloReceiver
 * @date 2019/4/23 11:42
 */
@Component
@Slf4j
@RabbitListener(queues = "direct_name")
public class DirectReceiver {

    @RabbitHandler
    public void processUser(User user, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("DirectReceiver收到消息：{}", user);
        // 手动ACK
        try {
//            //消息确认，代表消费者确认收到当前消息，语义上表示消费者成功处理了当前消息。
            channel.basicAck(tag, false);
//             代表消费者拒绝一条或者多条消息，第二个参数表示一次是否拒绝多条消息，第三个参数表示是否把当前消息重新入队
//        channel.basicNack(deliveryTag, false, false);

            // 代表消费者拒绝当前消息，第二个参数表示是否把当前消息重新入队
//        channel.basicReject(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void processString(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("收到消息：{}", message);
        // 手动ACK
        try {
//            //消息确认，代表消费者确认收到当前消息，语义上表示消费者成功处理了当前消息。
            channel.basicAck(tag, false);
//             代表消费者拒绝一条或者多条消息，第二个参数表示一次是否拒绝多条消息，第三个参数表示是否把当前消息重新入队
//        channel.basicNack(deliveryTag, false, false);

            // 代表消费者拒绝当前消息，第二个参数表示是否把当前消息重新入队
//        channel.basicReject(deliveryTag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
