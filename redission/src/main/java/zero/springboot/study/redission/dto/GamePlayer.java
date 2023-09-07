package zero.springboot.study.redission.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class GamePlayer implements Serializable {

    private String account;

    private Integer score;

    private LocalDateTime playScoreTime;

}
