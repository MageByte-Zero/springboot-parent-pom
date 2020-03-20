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
        publish("admin", "hey you must go now!");
    }

    /**
     * 推送消息到 topic
     *
     * @param publisher
     * @param message
     */
    public void publish(String publisher, String content) {
        log.info("message send {} by {}", content, publisher);
        SimpleMessage pushMsg = new SimpleMessage();
        pushMsg.setContent(content);
        pushMsg.setCreateTime(LocalDateTime.now());
        pushMsg.setPublisher(publisher);
        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }
}