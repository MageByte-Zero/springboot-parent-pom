package zero.springboot.study.websocket.push;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleMessage {
        private String userId;
        private String content;
        private LocalDateTime createTime;
}