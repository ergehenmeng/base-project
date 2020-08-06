package com.eghm.configuration.task.config;

import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2020/1/6 18:43
 */
@Data
public class CronDetail {

    /**
     * 任务标示符
     */
    private String nid;

    /**
     * 执行任务的bean的名称 必须实现Task接口
     */
    private String beanName;

    /**
     * 任务cron表达式
     */
    private String cronExpression;

}
