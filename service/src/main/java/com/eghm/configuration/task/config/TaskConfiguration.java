package com.eghm.configuration.task.config;

import com.eghm.service.common.TaskConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:49
 */
@Configuration
@EnableScheduling
public class TaskConfiguration {

    /**
     * 创建定时任务线程池
     */
    @Bean("taskScheduler")
    @Primary
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("定时任务-");
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.afterPropertiesSet();
        return scheduler;
    }

    /**
     * 自定义定时任务bean
     */
    @Bean
    public SystemTaskRegistrar systemTaskRegistrar(TaskConfigService taskConfigService){
        return new SystemTaskRegistrar(taskConfigService, taskScheduler());
    }
}
