package com.eghm.configuration.task.config;

import lombok.Data;

/**
 * 任务基本配置信息
 *
 * @author 殿小二
 * @date 2020/9/18
 */
@Data
public abstract class Task {

    /**
     * 执行任务的bean的名称 必须实现Task接口
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 入参
     */
    private String args;

    /**
     * 报警邮箱
     */
    private String alarmEmail;

    /**
     * 锁定时间
     */
    private long lockTime;
}
