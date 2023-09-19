package zero.springboot.study.redission.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.BloomFilterService;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissionApplication.class)
public class BloomFilterTest {

    @Autowired
    private BloomFilterService bloomFilterService;

    @Test
    public void testBloomFilter() {
        long expectedInsertions = 10000L;
        double falseProbability = 0.01;
        String filterName = "ipBlackList";

        boolean createFlag = bloomFilterService.create(filterName, expectedInsertions, falseProbability);
        log.info("Created bloom filter {}", createFlag);

        // 布隆过滤器增加元素
        for (long i = 0; i < expectedInsertions; i++) {
            bloomFilterService.add(filterName, i);
        }

        long elementCount = bloomFilterService.count(filterName);
        log.info("elementCount = {}.", elementCount);

        // 统计误判次数
        int count = 0;
        for (long i = expectedInsertions; i < expectedInsertions * 2; i++) {
            boolean contains = bloomFilterService.contains(filterName, i);
            if (contains) {
                count++;
            }
        }
        log.info("误判次数 = {}.", count);

        bloomFilterService.delete(filterName);

    }
}
