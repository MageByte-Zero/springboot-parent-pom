package zero.springboot.study.rabbitmq.fanout;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "fanout.A")
@Slf4j
public class FanoutReceiverA {

    @RabbitHandler
    public void process(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        log.info("fanout Receiver A  : {}" , message);
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