package zero.springboot.study.redission.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class StreamsService {
    @Autowired
    private RedissonClient redissonClient;

    private static final String STREAM_NAME = "order:doubleJoy";
    private static final String GROUP_NAME = "pointsGroup";
    private static final String CONSUMER_NAME = "pointsGroup";

    /**
     * 发送消息到队列头部
     *
     */
    public void sendMessage(Map<String, String> data) {
        RStream<String, String> stream = redissonClient.getStream(STREAM_NAME);
        stream.add(StreamAddArgs.entries(data));
    }

    /**
     * 消费者消费消息
     *
     * @param consumerName
     */
    public void consumerMessage(String consumerName) {
        RStream<String, String> stream = redissonClient.getStream(STREAM_NAME);

        stream.createGroup(GROUP_NAME, StreamMessageId.ALL);

        stream.readGroup(GROUP_NAME, consumerName, StreamReadGroupArgs.greaterThan(StreamMessageId.ALL));

        Map<StreamMessageId, Map<String, String>> messages = stream.readGroup("sensors_data", "consumer_1");
        for (Map.Entry<StreamMessageId, Map<String, String>> entry : messages.entrySet()) {
            Map<String, String> msg = entry.getValue();
            System.out.println(msg);

            stream.ack("sensors_data", entry.getKey());
        }

    }
}
