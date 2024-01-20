package zero.springboot.study.redission.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.QueueService;

@SpringBootTest(classes = RedissionApplication.class)
public class RedissionApplicationTests {

    @Autowired
    private QueueService queueService;

    @Test
    public void testQueue() throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                queueService.sendMessage("消息" + i);
            }
        }).start();

        new Thread(() -> queueService.onMessage()).start();

        Thread.currentThread().join();
    }


}
