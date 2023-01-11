package com.zero.disruptor.comsumer;

import com.zero.disruptor.BaseDisruptorConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MageByteConsumer extends BaseDisruptorConsumer<String> {
    private String name;

    public MageByteConsumer(String name) {
        this.name = name;
    }

    @Override
    public void consume(String obj , Long sequence, Boolean endOfBatch) {
        log.info("{} ：拿到队列中的数据： {}.", this.name, obj);
        //等待 500 ms
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
