/**
 * @FileName: ExecutorConfig.java
 * @Package: com.ziroom.kaka.common.config
 * @author wurt2
 * @created 2017/5/25 10:59
 * <p>
 * Copyright 2015 ziroom
 */
package com.eric.demo.config;

import com.eric.demo.task.ReportQueueProcess;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改�?			修改内容
 * </PRE>
 *
 * @author wurt2
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class ExecutorConfig {

    /**
     * Set the ThreadPoolExecutor's core pool size.
     */
    private static final int corePoolSize = 20;
    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     */
    private static final int maxPoolSize = 50;
    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     */
    private static final int queueCapacity = 1000;

    private static final String ThreadNamePrefix = "kakaExecutor-";

    @Bean(name = "kaka.executor")
    public Executor kakaExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        // 采用默认队列大小
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(ThreadNamePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任�?
        // CALLER_RUNS：不在新线程中执行任务，而是有调用�?�所在的线程来执�?
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(initMethod = "execute")
    public ReportQueueProcess reportQueueProcess() {
        return new ReportQueueProcess();
    }
}
