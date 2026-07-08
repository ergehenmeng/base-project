package com.eghm.query.sys;

import com.eghm.configuration.task.config.OnceScheduleBean;
import com.eghm.configuration.task.config.TaskRegistrar;
import com.eghm.service.sys.SysTaskScheduleGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Spring定时任务调度适配器
 *
 * @author 二哥很猛
 */
@Component
public class SpringSysTaskScheduleGateway implements SysTaskScheduleGateway {

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
