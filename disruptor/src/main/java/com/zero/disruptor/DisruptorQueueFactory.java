package com.zero.disruptor;

import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;


/**
 * 创建队列工厂，用于生成 {@link DisruptorQueue} 对象，并且支持“点对点”以及“发布订阅”这两种模式：
 *  Disruptor 提供了多个 WaitStrategy（等待策略）的实现，每种策略都具有不同性能和优缺点，根据实际运行环境的 CPU 的硬件特点选择恰当的策略，并配合特定的 JVM 的配置参数，能够实现不同的性能提升：
 * BlockingWaitStrategy 是最低效的策略，但其对 CPU 的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现；
 * SleepingWaitStrategy 的性能表现跟 BlockingWaitStrategy 差不多，对 CPU 的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景；
 * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略；例如：CPU 开启超线程的特性。
 */
public class DisruptorQueueFactory {

    private DisruptorQueueFactory() {}

    /**
     * 创建"点对电模式"的操作队列，即同一事件会被一组消费者其中之一消费
     * @param queueSize must be power of 2.
     * @param isMoreProducer
     * @param consumers
     * @param <T>
     * @return
     */
    public static <T> DisruptorQueue<T> getWorkPoolQueue(int queueSize, boolean isMoreProducer,
                                                             BaseDisruptorConsumer<T>... consumers) {
        Disruptor<ObjectEvent<T>> disruptor = new Disruptor(ObjectEventFactory.getInstance(),
                queueSize, DaemonThreadFactory.INSTANCE,
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(consumers);
        return new DisruptorQueue<T>(disruptor);
    }


    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     * @param queueSize must be power of 2.
     * @param isMoreProducer
     * @param consumers
     * @param <T>
     * @return
     */
    @SafeVarargs
    public static <T> DisruptorQueue<T> getPubSubQueue(int queueSize, boolean isMoreProducer,
                                                       BaseDisruptorConsumer<T>... consumers) {
        Disruptor<ObjectEvent<T>> disruptor = new Disruptor(ObjectEventFactory.getInstance(),
                queueSize, DaemonThreadFactory.INSTANCE,
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new SleepingWaitStrategy());
        disruptor.handleEventsWith(consumers);
        return new DisruptorQueue<T>(disruptor);
    }

    /**
     * 直接通过传入的 Disruptor 对象创建操作队列（如果消费者有依赖关系的话可以用此方法）
     * @param disruptor
     * @param <T>
     * @return
     */
    public static <T> DisruptorQueue<T> getQueue(Disruptor<ObjectEvent<T>> disruptor) {
        return new DisruptorQueue<T>(disruptor);
    }
}
