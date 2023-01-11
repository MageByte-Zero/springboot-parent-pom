package com.zero.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.List;

/**
 * 队列封装，用于初始化 disruptor 以及 ringBuffer 对象，并封装类一些常用的方法：
 *
 * @param <T>
 */
public class DisruptorQueue<T> {

    /**
     * 每个队列持有一个 disruptors 实例
     */
    private Disruptor<ObjectEvent<T>> disruptor;

    /**
     * disruptors 获取一个 buffer，用于发布事件
     */
    private RingBuffer<ObjectEvent<T>> ringBuffer;

    /**
     * 构建队列并启动
     *
     * @param disruptor
     */
    public DisruptorQueue(Disruptor<ObjectEvent<T>> disruptor) {
        this.disruptor = disruptor;
        this.ringBuffer = disruptor.getRingBuffer();
        this.disruptor.start();
    }

    public void put(T obj) {
        if (obj == null) {
            return;
        }
        long sequence = ringBuffer.next();
        try {
            ObjectEvent<T> objectEvent = ringBuffer.get(sequence);
            objectEvent.setObj(obj);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public void addAll(List<T> objList) {
        if (objList == null) {
            return;
        }

        for (T obj : objList) {
            if (obj != null) {
                this.put(obj);
            }
        }
    }

    public long cursor() {
        return this.disruptor.getRingBuffer().getCursor();
    }

    public void shutdown() {
        this.disruptor.shutdown();
    }

    public Disruptor<ObjectEvent<T>> getDisruptor() {
        return this.disruptor;
    }


    public RingBuffer<ObjectEvent<T>> getRingBuffer() {
        return this.ringBuffer;
    }

}
