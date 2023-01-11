package com.zero.disruptor;


import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * 消费者抽象类，所有自定义的消费者都需要继承这个抽象类，并实现 consume 方法（对获取的数据进行业务处理）
 */
public abstract class BaseDisruptorConsumer<T> implements EventHandler<ObjectEvent<T>>, WorkHandler<ObjectEvent<T>> {

    @Override
    public void onEvent(ObjectEvent<T> objectEvent, long sequence, boolean endOfBatch) throws Exception {
        this.consume(objectEvent.getObj(), sequence, endOfBatch);
    }

    /**
     * 子类实现
     * @param obj
     * @param sequence
     * @param endOfBatch
     */
    public abstract void consume(T obj, Long sequence, Boolean endOfBatch);

    @Override
    public void onEvent(ObjectEvent<T> event) throws Exception {
        this.consume(event.getObj(), null, null);
    }
}
