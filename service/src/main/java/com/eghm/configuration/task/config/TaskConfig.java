package com.eghm.configuration.task.config;

import com.eghm.service.common.SysTaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:49
 */
@EnableScheduling
public class TaskConfig {

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
    public SysTaskRegistrar systemTaskRegistrar(SysTaskService taskConfigService, TaskScheduler taskScheduler){
        return new SysTaskRegistrar(taskConfigService, taskScheduler);
    }
}
