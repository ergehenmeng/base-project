package com.eghm.configuration.task.config;

import com.eghm.annotation.EnableTask;
import com.eghm.mapper.SysTaskMapper;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 开启定时任务{@link EnableTask}后会自动激活该类
 * @author 二哥很猛
 * @since 2019/9/6 14:49
 */
@EnableScheduling
public class TaskConfig implements TaskSchedulerCustomizer {

    /**
     * 自定义定时任务bean
     */
    @Bean
    public SysTaskRegistrar systemTaskRegistrar(SysTaskMapper sysTaskMapper, TaskScheduler taskScheduler) {
        return new SysTaskRegistrar(sysTaskMapper, taskScheduler);
    }

    @Override
    public void customize(ThreadPoolTaskScheduler taskScheduler) {
        taskScheduler.setRemoveOnCancelPolicy(true);
    }
}
