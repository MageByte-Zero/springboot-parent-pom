package zero.springboot.study.redission.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.StreamsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissionApplication.class)
public class StreamTest {

    @Autowired
    private StreamsService streamsService;

    private final List<Map<String, String>> dataList = new ArrayList<>();

    @Before
    public void before() {
        streamsService.createGroup(StreamsService.GROUP_NAME);

        for (int i = 1; i <= 10; i++) {
            HashMap<String, String> data = new HashMap<>(16);
            data.put("orderID", String.valueOf(i));
            data.put("technician", String.valueOf(i + 67));
            data.put("amount", "598");

            dataList.add(data);
        }
    }

    @Test
    public void testSendAndConsumer() {
        new Thread(() -> {
            for (Map<String, String> data: dataList)
            streamsService.sendMessage(data);
        }).start();

        // 模拟两个消费者构成一个消费者组
        new Thread(() -> {
            try {
                streamsService.consumerMessage("consumer1");
            } catch (InterruptedException e) {
                log.error("消费执行异常", e);
            }
        });

        new Thread(() -> {
            try {
                streamsService.consumerMessage("consumer2");
            } catch (InterruptedException e) {
                log.error("消费执行异常", e);
            }
        });
    }

    @After
    public void after() {

    }


}
