package com.eghm.configuration.task.config;

import org.springframework.scheduling.config.CronTask;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:54
 */
public class CronSystemTask extends CronTask {

    /**
     * 任务的唯一nid用于打印日志等
     */
    private String nid;

    CronSystemTask(CronDetail config) {
        super(new RunnableTask(config), config.getCronExpression());
        this.nid = config.getNid();
    }

    public String getNid() {
        return nid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CronSystemTask that = (CronSystemTask) o;
        return nid != null && nid.equals(that.nid);
    }

    @Override
    public int hashCode() {
        return nid != null ? nid.hashCode() : 0;
    }
}
