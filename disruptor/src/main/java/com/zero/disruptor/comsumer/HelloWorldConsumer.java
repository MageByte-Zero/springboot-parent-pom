package com.zero.disruptor.comsumer;

import com.zero.disruptor.BaseDisruptorConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HelloWorldConsumer extends BaseDisruptorConsumer<String> {
    private String name;

    public HelloWorldConsumer(String name) {
        this.name = name;
    }

    @Override
    public void consume(String obj, Long sequence, Boolean endOfBatch) {
        log.info("{} ：拿到队列中的数据： {}.", this.name, obj);
        //等待1秒钟
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
