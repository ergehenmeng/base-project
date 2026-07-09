package com.eghm.domain.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定时任务
 *
 * @author 二哥很猛
 */
@Data
public class SysTask {

    /** 主键 */
    private Long id;

    /** 定时任务名称 */
    private String title;

    /** 类的bean名称 */
    private String beanName;

    /** bean的方法名(单个类中不能有重载方法,有则默认取第一个方法执行) */
    private String methodName;

    /** 方法入参 */
    private String args;

    /** cron表达式 */
    private String cronExpression;

    /** 状态 0:关闭 1:开启 */
    private Boolean state;

    /** 报警邮箱地址 */
    private String alarmEmail;

    /** 锁持有时间,毫秒 */
    private Long lockTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 备注信息 */
    private String remark;

    public void initialize(String title, String beanName, String methodName, String args, String cronExpression,
                           Boolean state, String alarmEmail, Long lockTime, String remark) {
        this.title = title;
        this.beanName = beanName;
        this.methodName = methodName;
        this.args = args;
        this.cronExpression = cronExpression;
        this.state = state;
        this.alarmEmail = alarmEmail;
        this.lockTime = lockTime;
        this.remark = remark;
    }

    public void enable() {
        this.state = true;
    }

    public void disable() {
        this.state = false;
    }

    public boolean isEnabled() {
        return Boolean.TRUE.equals(this.state);
    }

    public void changeCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        this.updateTime = LocalDateTime.now();
    }
}
