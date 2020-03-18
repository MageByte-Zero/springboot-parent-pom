package zero.springboot.study.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.async.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsyncApplication.class)
public class AsyncApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void testAsync() {
        orderService.doShop();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
