package com.eghm.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.boot.task.ThreadPoolTaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.AbstractMessageBrokerConfiguration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 线程池配置
 * 为了在此配置线程池呢? 因为SpringBoot内置的线程池是在{@link ConditionalOnMissingBean} 时才会创建,
 * 由于项目集成了Websocket, 内部会自动创建线程池 {@link AbstractMessageBrokerConfiguration#messageBrokerTaskScheduler} 导致内置线程池无法创建,且相关的线程池的配置接口都无效
 * @author 二哥很猛
 * @since 2024/9/23
 */

@Configuration
public class ExecutorConfig implements ThreadPoolTaskSchedulerCustomizer {

    /**
     * Async 线程池
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor(ThreadPoolTaskExecutorBuilder builder) {
        return builder.build();
    }

    /**
     * 定时任务线程池
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler(ThreadPoolTaskSchedulerBuilder builder) {
        return builder.build();
    }

    @Override
    public void customize(ThreadPoolTaskScheduler taskScheduler) {
        taskScheduler.setRemoveOnCancelPolicy(true);
    }
}
