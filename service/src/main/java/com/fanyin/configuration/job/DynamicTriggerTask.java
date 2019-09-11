package com.fanyin.configuration.job;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:54
 */
public class DynamicTriggerTask extends TriggerTask {

    private String nid;

    private String cronExpression;


    DynamicTriggerTask(String nid,String beanName, String cronExpression) {
        super(new DynamicRunnable(beanName,nid),new CronTrigger(cronExpression));
        this.nid = nid;
        this.cronExpression = cronExpression;
    }

    public String getNid() {
        return nid;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        DynamicTriggerTask that = (DynamicTriggerTask) o;
        return nid != null && nid.equals(that.nid);
    }

    @Override
    public int hashCode() {
        return nid != null ? nid.hashCode() : 0;
    }
}
