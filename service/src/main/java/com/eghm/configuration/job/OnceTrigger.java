package com.eghm.configuration.job;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:31
 */
public class OnceTrigger implements Trigger {

    /**
     * 任务执行开始时间
     */
    private Date executeTime;

    /**
     * 任务是否执行过
     */
    private volatile boolean executed = false;

    public OnceTrigger(Date executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public Date nextExecutionTime(@NonNull TriggerContext triggerContext) {
        if(executed){
            return null;
        }
        executed = true;
        return executeTime;
    }
}
