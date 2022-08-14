package zero.springboot.study.redission.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.PubSubService;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissionApplication.class)
public class PubSubServiceTest {

    @Autowired
    private PubSubService pubSubService;

    @Test
    public void test() throws InterruptedException {

        // 消费者接受消息
        new Thread(() -> {
            while (true) {
                pubSubService.onMessage();
            }
        }).start();


        // 使用其他线程模拟生产者
        new Thread(() -> pubSubService.sendMessage("么么哒")).start();
        TimeUnit.SECONDS.sleep(2);
        pubSubService.onMessage();
        TimeUnit.SECONDS.sleep(2);

    }
}
