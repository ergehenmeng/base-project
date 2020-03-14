package com.eghm.configuration.task.config;

import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:54
 */
public class CronTriggerTask extends TriggerTask {

    private String nid;

    private String cronExpression;


    CronTriggerTask(CronTask config) {
        super(new RunnableTask(config.getNid(), config.getBeanName()), new CronTrigger(config.getCronExpression()));
        this.nid = config.getNid();
        this.cronExpression = config.getCronExpression();
    }

    public String getNid() {
        return nid;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CronTriggerTask that = (CronTriggerTask) o;
        return nid != null && nid.equals(that.nid);
    }

    @Override
    public int hashCode() {
        return nid != null ? nid.hashCode() : 0;
    }
}
