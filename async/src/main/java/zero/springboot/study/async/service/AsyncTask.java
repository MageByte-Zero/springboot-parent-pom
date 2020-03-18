/**
 * Project: springboot-parent-pom
 * File created at 2019/5/16 19:56
 */
package zero.springboot.study.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName AsyncTask
 * @date 2019/5/16 19:56
 */
@Component
@Slf4j
public class AsyncTask {
    public static Random random = new Random();


    @Async("taskExecutor")
    public void sendSms() throws InterruptedException {
        log.info("开始做任务2：发送短信");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务1，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    public Future<String> pay() throws InterruptedException {
        log.info("开始做异步返回结果任务2：支付");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务2，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("会员服务完成");
    }

    /**
     * 返回结果的异步调用
     * @throws InterruptedException
     */
    @Async("taskExecutor")
    public void vip() throws InterruptedException {
        log.info("开始做任务5：会员");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("开始做异步返回结果任务5，耗时：" + (end - start) + "毫秒");
    }
}
