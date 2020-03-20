package zero.springboot.study.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WebsocketSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketSimpleApplication.class, args);
    }

}
