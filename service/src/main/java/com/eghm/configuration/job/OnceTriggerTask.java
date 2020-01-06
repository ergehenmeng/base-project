package com.eghm.configuration.job;

import com.eghm.common.utils.DateUtil;
import org.springframework.scheduling.config.TriggerTask;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:28
 */
public class OnceTriggerTask extends TriggerTask {

    private Date executeTime;

    /**
     * 针对执行一次的任务,在执行时间开始,超过60s后会被标记移除状态,等待从定时任务队列中删除
     */
    private static final int REMOVE_TIME = 60;

    OnceTriggerTask(OnceTask config) {
        super(new RunnableTask(config.getNid(), config.getBeanName()), new OnceTrigger(config.getExecuteTime()));
        this.executeTime = config.getExecuteTime();
    }

    /**
     * 该任务是否需要被移除
     * @param nowTime 当前时间 如果为空则默认系统时间
     * @return 是否需要移除该任务
     */
    public boolean shouldRemove(Date nowTime){
        if (nowTime == null) {
            nowTime = DateUtil.getNow();
        }
        return DateUtil.addSeconds(executeTime,REMOVE_TIME).before(nowTime);
    }
}
