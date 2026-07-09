package com.eghm.infrastructure.persistence.mybatis.system.schedule;

import com.eghm.infrastructure.persistence.schedule.config.OnceScheduleBean;
import com.eghm.infrastructure.persistence.schedule.config.TaskRegistrar;
import com.eghm.application.system.schedule.SysTaskScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Spring定时任务调度适配器
 *
 * @author 二哥很猛
 */
@Component
public class SpringSysTaskScheduleService implements SysTaskScheduleService {

    private TaskRegistrar taskRegistrar;

    @Autowired(required = false)
    public void setTaskRegistrar(TaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    @Override
    public boolean scheduleOnce(String beanName, String methodName, String args, LocalDateTime executeTime) {
        if (taskRegistrar == null) {
            return false;
        }
        OnceScheduleBean onceDetail = new OnceScheduleBean();
        onceDetail.setBeanName(beanName);
        onceDetail.setMethodName(methodName);
        onceDetail.setArgs(args);
        onceDetail.setExecuteTime(executeTime);
        taskRegistrar.addTask(onceDetail);
        return true;
    }
}
