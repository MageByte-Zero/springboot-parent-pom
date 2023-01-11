package com.zero.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 事件工厂，用于创造事件。
 * Disruptor 通过 EventFactory 在 RingBuffer 中预创建 Event 的实例。一个 Event 实例实际上被用作一个“数据槽”，发布者发布前，
 * 先从 RingBuffer 获得一个 Event 的实例，然后往 Event 实例中填充数据，之后再发布到 RingBuffer 中，之后由 Consumer 获得该 Event 实例并从中读取数据。
 *
 * @param <T>
 */
public class ObjectEventFactory<T> implements EventFactory<ObjectEvent<T>> {

    private ObjectEventFactory() {
    }

    private static class ObjectEventFactoryHolder {
        private static ObjectEventFactory objectEventFactory = new ObjectEventFactory();
    }

    public static ObjectEventFactory getInstance() {
        return ObjectEventFactoryHolder.objectEventFactory;
    }

    @Override
    public ObjectEvent<T> newInstance() {
        return new ObjectEvent<T>();
    }
}
