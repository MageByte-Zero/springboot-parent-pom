package zero.springboot.study.redission.service;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterService {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 创建布隆过滤器
     * @param filterName 过滤器名称
     * @param expectedInsertions 预测插入数量
     * @param falseProbability 误判率
     * @param <T>
     * @return
     */
    public <T> RBloomFilter<T> create(String filterName, long expectedInsertions, double falseProbability) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        bloomFilter.tryInit(expectedInsertions, falseProbability);
        return bloomFilter;
    }

}
