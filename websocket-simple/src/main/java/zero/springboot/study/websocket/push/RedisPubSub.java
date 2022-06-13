package zero.springboot.study.websocket.push;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RedisPubSub {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * topic
     */
    private ChannelTopic topic = new ChannelTopic("/redis/pubsub");

    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    private void schedule() {
        log.info("publish message");
        publish("221", "hey you must go now!");
    }

    /**
     * 推送消息到 topic,对应的监听器监听消息，并判断当前节点是否存有 userID 对应的连接，有则推送消息给客户端
     * @see MessageSubscriber#onMessage(zero.springboot.study.websocket.push.SimpleMessage, java.lang.String)
     * @param userId
     * @param content
     */
    public void publish(String userId, String content) {
        log.info("message send {} to {}", content, userId);
        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setCreateTime(LocalDateTime.now());
        pushMsg.setUserId(userId);
        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }
}