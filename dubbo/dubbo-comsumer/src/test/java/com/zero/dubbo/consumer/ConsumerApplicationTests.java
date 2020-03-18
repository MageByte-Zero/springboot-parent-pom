package com.zero.dubbo.consumer;

import com.zero.dubbo.consumer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApplication.class)
@Slf4j
public class ConsumerApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        log.info("user info ={}", userService.listUser());
    }

}
