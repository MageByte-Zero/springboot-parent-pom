package com.zero.caffeine.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池
 */
@Configuration
@EnableAsync
public class AsyncExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private final int corePoolSize = 8;
    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private final int maxPoolSize = 16;
    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private final int queueCapacity = 2000;


    private final int keepAliveSeconds = 60 * 30;


    @Bean("cacheExecutor")
    public ThreadPoolTaskExecutor cacheExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("cacheExecutor-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(keepAliveSeconds);

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


}  
