package zero.springboot.study.redission.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zero.springboot.study.redission.RedissionApplication;
import zero.springboot.study.redission.service.HyperLogLogService;

import java.util.ArrayList;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedissionApplication.class)
public class HyperLogLogTest {

    @Autowired
    private HyperLogLogService hyperLogLogService;

    @Test
    public void testAdd() {
        String logName = "码哥字节:Redis为什么这么快:uv";
        String item = "肖菜鸡";
        hyperLogLogService.add(logName, item);
        log.info("添加元素[{}]到 log [{}] 中。", item, logName);
    }

    @Test
    public void testCount() {
        String logName = "码哥字节:Redis为什么这么快:uv";
        long count = hyperLogLogService.count(logName);
        log.info("logName = {} count = {}.", logName, count);
    }

    @Test
    public void testMerge() {
        ArrayList<String> items = new ArrayList<>();
        items.add("肖菜鸡");
        items.add("谢霸哥");
        items.add("陈小白");

        String otherLogName = "码哥字节:Redis多线程模型原理与实战:uv";
        hyperLogLogService.addAll(otherLogName, items);
        log.info("添加 {} 个元素到 log [{}] 中。", items.size(), otherLogName);

        String logName = "码哥字节:Redis为什么这么快:uv";
        hyperLogLogService.merge(logName, otherLogName);
        log.info("将 {} 合并到 {}.", otherLogName, logName);

        long count = hyperLogLogService.count(logName);
        log.info("合并后的 count = {}.", count);
    }
}
