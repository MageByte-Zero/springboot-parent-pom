package zero.springboot.study.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.rabbitmq.direct.DirectSender;
import zero.springboot.study.rabbitmq.fanout.FanoutSender;
import zero.springboot.study.rabbitmq.simple.HelloSender;
import zero.springboot.study.rabbitmq.topic.TopicSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqApplicationTests {

    @Autowired
    private DirectSender directSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private HelloSender helloSender;

    @Test
    public void testDirect() {
        directSender.send();
    }

    @Test
    public void topic1() {
        topicSender.send1();
    }

    @Test
    public void topic2() {
        topicSender.send2();
    }

    @Test
    public void testFanout() {
        fanoutSender.send();
    }

    @Test
    public void testSimple() {
        helloSender.send();
    }

}
