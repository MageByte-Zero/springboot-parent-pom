package zero.springboot.study.redission.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PubSubService {

    private static final String CHANNEL = "smile.girls.Tina";

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 发布消息到 Topic
     * @param message 消息
     * @return 接收消息的客户端数量
     */
    public long sendMessage(String message) {
        RTopic topic = redissonClient.getTopic(CHANNEL);
        long publish = topic.publish(message);
        log.info("生产者发送消息成功，msg = {}", message);
        return publish;
    }

    /**
     *
     */
    public void onMessage() {
        // in other thread or JVM
        RTopic topic = redissonClient.getTopic(CHANNEL);
        topic.addListener(String.class, (channel, msg) -> {
            log.info("channel: {} 收到消息 {}.",  channel, msg);
        });
    }
}
