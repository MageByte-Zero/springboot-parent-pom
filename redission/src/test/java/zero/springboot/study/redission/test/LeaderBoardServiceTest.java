package zero.springboot.study.redission.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.dto.GamePlayer;
import zero.springboot.study.redission.service.LeaderBoardService;

import java.time.LocalDateTime;
import java.util.Collection;

@Slf4j
@SpringBootTest(classes = RedissionApplication.class)
public class LeaderBoardServiceTest {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @Test
    public void testUpdate() {
        String key = "leaderboard";
        int dataId = 360;

        GamePlayer gamePlayer = new GamePlayer()
                .setAccount("码哥字节")
                .setScore(19910228)
                .setPlayScoreTime(LocalDateTime.of(2023, 7, 9, 10,10,10));
        leaderBoardService.saveOrUpdate(key, dataId, gamePlayer);

        GamePlayer gamePlayer2 = new GamePlayer()
                .setAccount("因姐er")
                .setScore(19930607)
                .setPlayScoreTime(LocalDateTime.of(2023, 7, 10, 10,10,10));
        leaderBoardService.saveOrUpdate(key, dataId, gamePlayer2);

        GamePlayer gamePlayer3 = new GamePlayer()
                .setAccount("码哥字节")
                .setScore(19910412)
                .setPlayScoreTime(LocalDateTime.of(2023, 7, 9, 10,10,10));
        leaderBoardService.saveOrUpdate(key, dataId, gamePlayer3);

        GamePlayer gamePlayer4 = new GamePlayer()
                .setAccount("偷心G")
                .setScore(19930607)
                .setPlayScoreTime(LocalDateTime.of(2023, 6, 9, 10,10,10));
        leaderBoardService.saveOrUpdate(key, dataId, gamePlayer4);
    }

    @Test
    public void testGetTopN() {
        String key = "leaderboard";
        int dataId = 360;

        Collection<String> topN = leaderBoardService.getTopN(key, dataId, 2);
        log.info(JSON.toJSONString(topN));
    }

    @Test
    public void testGetPlayerRank() {
        String key = "leaderboard";
        int dataId = 360;

        Integer playerRank = leaderBoardService.getPlayerRank(key, dataId, "因姐er");
        log.info("账号【因姐er】排名 {}", playerRank);
    }

    @Test
    public void testDeletePlayer() {
        String key = "leaderboard";
        int dataId = 360;

        boolean flag = leaderBoardService.deletePlayer(key, dataId, "偷心G");
        System.out.println(flag);

    }
}
