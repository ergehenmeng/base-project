package com.eghm.platform.job.config.task.config;

import com.eghm.platform.job.annotation.EnableSchedulingTask;
import com.eghm.foundation.core.service.AlarmService;
import com.eghm.foundation.core.lock.RedisLock;
import com.eghm.platform.job.mapper.SysTaskMapper;
import com.eghm.platform.job.service.SysTaskLogService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启定时任务{@link EnableSchedulingTask}后会自动激活该类
 * @author 二哥很猛
 * @since 2019/9/6 14:49
 */
@EnableScheduling
public class SchedulingConfig {

    /**
     * 自定义定时任务bean
     */
    @Bean
    public TaskRegistrar taskRegistrar(RedisLock redisLock, SysTaskMapper sysTaskMapper, @Qualifier("taskScheduler") TaskScheduler taskScheduler,
                                       AlarmService alarmService, SysTaskLogService sysTaskLogService) {
        return new TaskRegistrar(redisLock, alarmService, sysTaskMapper, taskScheduler, sysTaskLogService);
    }

}