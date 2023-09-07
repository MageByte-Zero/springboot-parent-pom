package zero.springboot.study.redission.service;

import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zero.springboot.study.redission.dto.GamePlayer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

/**
 * 排行榜
 *
 * @author magebte
 */
@Service
public class LeaderBoardService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 10000-01-01 01:01:01 秒数作为基准时间
     */
    private static final Long BASE_TIME = LocalDateTime.of(10000, 1, 1, 1, 1, 1)
            .toEpochSecond(ZoneOffset.of("+8"));

    /**
     * 更新或者插入玩家排行信息
     *
     * @param key        the key
     * @param dataId     数据中心
     * @param gamePlayer 玩家信息
     * @return
     */
    public Boolean saveOrUpdate(String key, int dataId, GamePlayer gamePlayer) {
        RScoredSortedSet<String> scoredSortedSet = this.getRScoredSortedSet(key, dataId);

        double score = this.calcScore(gamePlayer.getScore(), gamePlayer.getPlayScoreTime().toEpochSecond(ZoneOffset.of("+8")));
        return scoredSortedSet.add(score, gamePlayer.getAccount());

    }

    /**
     * 获取 Top N 玩家排行信息
     *
     * @param key
     * @param dataId
     * @param n 个数
     * @return
     */
    public Collection<String> getTopN(String key, int dataId, int n) {
        RScoredSortedSet<String> scoredSortedSet = this.getRScoredSortedSet(key, dataId);

        return scoredSortedSet.valueRangeReversed(0, n - 1);
    }

    /**
     * 获取指定玩家排名
     *
     * @param key
     * @param dataId
     * @param account
     * @return 从 0 开始
     */
    public Integer getPlayerRank(String key, int dataId, String account) {
        RScoredSortedSet<String> scoredSortedSet = this.getRScoredSortedSet(key, dataId);

        return scoredSortedSet.revRank(account);
    }

    /**
     * 删除玩家排行信息
     *
     * @param key
     * @param dataId  数据中心
     * @param account 玩家账号
     * @return
     */
    public boolean deletePlayer(String key, int dataId, String account) {
        RScoredSortedSet<String> scoredSortedSet = this.getRScoredSortedSet(key, dataId);

        return scoredSortedSet.remove(account);
    }

    /**
     * 获取排序集合
     *
     * @param key
     * @param dataId
     * @return
     */
    private RScoredSortedSet<String> getRScoredSortedSet(String key, int dataId) {
        String sortedSetKey = "dataId:" + dataId + ":" + key;

        return redissonClient.getScoredSortedSet(sortedSetKey);
    }

    /**
     * 玩家游戏分 + ((基准时间 - 玩家获得某分数时间) / 基准时间)，就实现了分数相同，先达到该分数的排在前面的功能
     *
     * @param playerScore     玩家游戏分
     * @param playerScoreTime 分数获得时间
     * @return
     */
    private double calcScore(int playerScore, long playerScoreTime) {
        return playerScore + (BASE_TIME - playerScoreTime) * 1.0 / BASE_TIME;
    }
}
