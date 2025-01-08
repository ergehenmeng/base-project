package com.eghm.configuration.task.config;

import com.eghm.annotation.EnableTask;
import com.eghm.mapper.SysTaskMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启定时任务{@link EnableTask}后会自动激活该类
 * @author 二哥很猛
 * @since 2019/9/6 14:49
 */
@EnableScheduling
public class SchedulingConfig {

    /**
     * 自定义定时任务bean
     */
    @Bean
    public SysTaskRegistrar systemTaskRegistrar(SysTaskMapper sysTaskMapper, @Qualifier("taskScheduler") TaskScheduler taskScheduler) {
        return new SysTaskRegistrar(sysTaskMapper, taskScheduler);
    }

}
