package com.eghm.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerBuilder;
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
public class ExecutorConfig implements TaskExecutorCustomizer {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(TaskExecutorBuilder builder) {
        return builder.build();
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
        return builder.build();
    }

    @Override
    public void customize(ThreadPoolTaskExecutor taskExecutor) {
        taskExecutor.setThreadNamePrefix("异步线程-");
    }
}
