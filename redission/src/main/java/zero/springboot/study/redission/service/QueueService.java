package zero.springboot.study.redission.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.redisson.api.queue.DequeMoveArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class QueueService {

    @Autowired
    private RedissonClient redissonClient;

    private static final String ORDER_PAY_QUEUE = "order:pay";
    private static final String ORDER_PAY_BACK_QUEUE = "order:pay:back";

    /**
     * 发送消息到队列头部
     *
     * @param message
     */
    public void sendMessage(String message) {
        RBlockingDeque<String> orderPayQueue = redissonClient.getBlockingDeque(ORDER_PAY_QUEUE);

        try {
            orderPayQueue.putFirst(message);
            log.info("将消息: {} 插入到队列 {}。", message, ORDER_PAY_QUEUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从队列尾部阻塞当时获取消息
     */
    public void onMessage() {

        RBlockingDeque<String> orderPayQueue = redissonClient.getBlockingDeque(ORDER_PAY_QUEUE);
        while (true) {
            // BLMOVE order:pay order:pay:back RIGHT LEFT 0
            String message = orderPayQueue.move(Duration.ofSeconds(0), DequeMoveArgs.pollLast()
                    .addFirstTo(ORDER_PAY_BACK_QUEUE));
            log.info("从队列 {} 中读取到消息：{}，并把消息复制到 {} 队列.", ORDER_PAY_QUEUE, message, ORDER_PAY_BACK_QUEUE);

            // 消费正常，从 ORDER_PAY_BACK_QUEUE 删除这条消息，LREM order:pay:back 0 message
            removeBackQueueMessage(message, ORDER_PAY_BACK_QUEUE);
        }
    }

    /**
     * 从队列中删除消息
     * @param message
     * @param queueName
     */
    private void removeBackQueueMessage(String message, String queueName) {
        RBlockingDeque<String> orderPayBackDeque = redissonClient.getBlockingDeque(queueName);
        boolean remove = orderPayBackDeque.remove(message);
        log.info("消费正常，删除队列 {} 的消息 {}。", queueName, message);
    }
}
