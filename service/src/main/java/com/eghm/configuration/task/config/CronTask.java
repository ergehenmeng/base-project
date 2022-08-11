package com.eghm.configuration.task.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:43
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CronTask extends Task {

    /**
     * 任务cron表达式
     */
    private String cronExpression;

}
