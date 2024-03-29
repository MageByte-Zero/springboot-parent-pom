package zero.springboot.study.redission.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.PendingEntry;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamAddArgs;
import org.redisson.api.stream.StreamCreateGroupArgs;
import org.redisson.api.stream.StreamReadGroupArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StreamsService {
    @Autowired
    private RedissonClient redissonClient;

    public static final String STREAM_NAME = "order:doubleJoy";
    public static final String GROUP_NAME = "pointsGroup";

    /**
     * 创建消费组
     *
     * @param groupName
     */
    public void createGroup(String groupName) {
        RStream<String, String> stream = redissonClient.getStream(STREAM_NAME);
        stream.createGroup(StreamCreateGroupArgs.name(groupName).id(StreamMessageId.ALL).makeStream());
        log.info("创建消费组 = {} 成功。", groupName);
    }

    /**
     * 生产者发送消息到队列头部
     *
     * @param data 消息体
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
    public void consumerMessage(String consumerName) throws InterruptedException {
        while (true) {
            RStream<String, String> stream = redissonClient.getStream(STREAM_NAME);

            // 指定的 groupName 消费者组的消费者消费数据，每次读取一条,timeout = 0 阻塞等待。
            // XREADGROUP GROUP pointsGroup consumerName COUNT 1 BLOCK 0 STREAMS order:doubleJoy >
            Map<StreamMessageId, Map<String, String>> messages = stream.readGroup(GROUP_NAME, consumerName
                    , StreamReadGroupArgs.neverDelivered().count(1).timeout(Duration.ZERO));

            for (Map.Entry<StreamMessageId, Map<String, String>> entry : messages.entrySet()) {
                Map<String, String> msg = entry.getValue();
                StreamMessageId messageId = entry.getKey();

                // 模拟执行业务逻辑
                TimeUnit.MILLISECONDS.sleep(100L);
                // 消费成功，执行 ACK
                stream.ack(GROUP_NAME, messageId);
                log.info("消费者【{}】消费消息成功，消息 ID = {}, 消息内容 = {}。", consumerName, messageId.toString(), JSON.toJSONString(msg));
            }
        }

    }

    /**
     * 查询已消费但是未 ACK 的数据。
     *
     * @param startId
     * @param endId
     * @param count
     * @return
     */
    public List<PendingEntry> listPending(StreamMessageId startId, StreamMessageId endId, int count) {
        return redissonClient.getStream(STREAM_NAME).listPending(GROUP_NAME, startId, endId, count);
    }
}
