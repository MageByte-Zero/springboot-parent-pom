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
    public <T> boolean create(String filterName, long expectedInsertions, double falseProbability) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.tryInit(expectedInsertions, falseProbability);
    }

    /**
     * 添加元素到布隆过滤器
     * @param filterName
     * @param element
     * @return true if element has been added successfully false if element is already present
     */
    public <T> boolean add(String filterName, T element) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.add(element);
    }

    /**
     * 判断元素是否存在布隆过滤器中
     * @param filterName
     * @param element
     * @return true if element is present false if element is not present
     */
    public <T> boolean contains(String filterName, T element) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.contains(element);
    }

    /**
     * 计算布隆过滤器的元素个数
     * @param filterName
     * @return
     */
    public <T> long count(String filterName) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.count();
    }

    public <T> boolean delete(String filterName) {
        RBloomFilter<T> bloomFilter = redissonClient.getBloomFilter(filterName);
        return bloomFilter.delete();
    }

}
