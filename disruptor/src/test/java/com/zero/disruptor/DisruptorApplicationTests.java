package com.zero.disruptor;

import com.zero.disruptor.comsumer.HelloWorldConsumer;
import com.zero.disruptor.comsumer.MageByteConsumer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DisruptorApplication.class)
@Slf4j
public class DisruptorApplicationTests {

    @Test
    public void testPubSubQueue() {
        HelloWorldConsumer helloWorldConsumer = new HelloWorldConsumer("HelloWorldConsumer");
        MageByteConsumer mageByteConsumer = new MageByteConsumer("MageByteConsumer");

        DisruptorQueue<String> disruptorQueue = DisruptorQueueFactory.getPubSubQueue(4, false
                , helloWorldConsumer, mageByteConsumer);

        disruptorQueue.put("师姐你好");

        //等待 2 秒钟
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testWorkPoolQueue() {
        HelloWorldConsumer helloWorldConsumer = new HelloWorldConsumer("HelloWorldConsumer");
        MageByteConsumer mageByteConsumer = new MageByteConsumer("MageByteConsumer");

        DisruptorQueue<String> disruptorQueue = DisruptorQueueFactory.getWorkPoolQueue(4, false
                , helloWorldConsumer, mageByteConsumer);

        disruptorQueue.put("师姐你好");

        //等待 2 秒钟
        try {
            TimeUnit.SECONDS.sleep(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
