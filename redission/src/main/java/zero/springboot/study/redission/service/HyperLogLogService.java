package zero.springboot.study.redission.service;

import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HyperLogLogService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 将数据添加到 HyperLogLog
     *
     * @param logName
     * @param item
     * @param <T>
     */
    public <T> void add(String logName, T item) {
        RHyperLogLog<T> hyperLogLog = redissonClient.getHyperLogLog(logName);
        hyperLogLog.add(item);
    }

    /**
     * 将集合数据添加到 HyperLogLog
     * @param logName
     * @param items
     * @param <T>
     */
    public <T> void addAll(String logName, List<T> items) {
        RHyperLogLog<T> hyperLogLog = redissonClient.getHyperLogLog(logName);
        hyperLogLog.addAll(items);
    }

    /**
     * 将 otherLogNames 的 log 合并到 logName
     *
     * @param logName       当前 log
     * @param otherLogNames 需要合并到当前 log 的其他 logs
     * @param <T>
     */
    public <T> void merge(String logName, String... otherLogNames) {
        RHyperLogLog<T> hyperLogLog = redissonClient.getHyperLogLog(logName);
        hyperLogLog.mergeWith(otherLogNames);
    }

    /**
     * 统计基数
     *
     * @param logName 需要统计的 logName
     * @param <T>
     * @return
     */
    public <T> long count(String logName) {
        RHyperLogLog<T> hyperLogLog = redissonClient.getHyperLogLog(logName);
        return hyperLogLog.count();
    }
}
