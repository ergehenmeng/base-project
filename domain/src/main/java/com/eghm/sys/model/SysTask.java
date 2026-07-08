package com.eghm.sys.model;

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

}
