package com.eghm.configuration.task.config;

import com.eghm.common.utils.DateUtil;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.config.TriggerTask;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:28
 */
public class OnceTriggerTask extends TriggerTask {

    private Date executeTime;

    OnceTriggerTask(OnceTask config) {
        super(new RunnableTask(config.getNid(), config.getBeanName()), new OnceTrigger(config.getExecuteTime()));
        this.executeTime = config.getExecuteTime();
    }

    /**
     * 该任务是否需要被移除
     * @param nowTime 当前时间 如果为空则默认系统时间
     * @param maxSurvivalTime 最大存活时间
     * @return 是否需要移除该任务
     */
    protected boolean shouldRemove(Date nowTime, int maxSurvivalTime){
        if (nowTime == null) {
            nowTime = DateUtil.getNow();
        }
        return DateUtil.addSeconds(executeTime, maxSurvivalTime).before(nowTime);
    }

    static class OnceTrigger implements Trigger {

        private boolean executed = false;

        private Date executeTime;

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
}


