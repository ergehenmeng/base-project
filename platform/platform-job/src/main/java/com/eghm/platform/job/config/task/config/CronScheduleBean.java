package com.eghm.platform.job.config.task.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2020/1/6 18:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CronScheduleBean extends ScheduleBean {

    /**
     * 任务cron表达式
     */
    private String cronExpression;

}
