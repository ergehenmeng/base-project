package com.eghm.infrastructure.persistence.schedule.config;

import com.eghm.infrastructure.persistence.schedule.EnableSchedulingTask;
import com.eghm.domain.shared.service.AlarmService;
import com.eghm.application.shared.lock.RedisLock;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskMapper;
import com.eghm.application.system.service.SysTaskLogApplicationService;
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
                                       AlarmService alarmService, SysTaskLogApplicationService sysTaskLogService) {
        return new TaskRegistrar(redisLock, alarmService, sysTaskMapper, taskScheduler, sysTaskLogService);
    }

}