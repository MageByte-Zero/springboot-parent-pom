/**
 * Project: springboot-parent-pom
 * File created at 2019/5/16 19:53
 */
package zero.springboot.study.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author lijianqing
 * @version 1.0
 * @ClassName OrderService
 * @date 2019/5/16 19:53
 */
@Slf4j
@Service
public class OrderService {
    public static Random random = new Random();


    @Autowired
    private AsyncTask asyncTask;

    public void doShop() {
        try {
            createOrder();
            Future<String> pay = asyncTask.pay();
            if (pay.isDone()) {
                try {
                    String result = pay.get();
                    log.info("异步任务返回结果{}", result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                asyncTask.vip();
                asyncTask.sendSms();
            }
            otherJob();
        } catch (InterruptedException e) {
            log.error("异常", e);
        }
    }

    public void createOrder() {
        log.info("开始做任务1：下单成功");
    }

    /**
     * 错误使用，不会异步执行：调用方与被调方不能在同一个类。主要是使用了动态代理，同一个类的时候直接调用，不是通过生成的动态代理类调用
     */
    @Async("taskExecutor")
    public void otherJob() {
        log.info("开始做任务4：物流");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成任务4，耗时：" + (end - start) + "毫秒");
    }

}
