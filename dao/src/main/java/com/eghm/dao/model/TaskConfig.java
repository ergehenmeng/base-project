package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TaskConfig implements Serializable {


    /**
     * 定时任务名称<br>
     * 表 : job_task<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 定时任务nid<br>
     * 表 : job_task<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 类的bean名称<br>
     * 表 : job_task<br>
     * 对应字段 : bean_name<br>
     */
    private String beanName;

    /**
     * cron表达式<br>
     * 表 : job_task<br>
     * 对应字段 : cron_expression<br>
     */
    private String cronExpression;

    /**
     * 状态 0:关闭 1:开启<br>
     * 表 : job_task<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 报警邮箱地址<br>
     * 表 : job_task<br>
     * 对应字段 : alarm_email<br>
     */
    private String alarmEmail;

    /**
     * 更新时间<br>
     * 表 : job_task<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注信息<br>
     * 表 : job_task<br>
     * 对应字段 : remark<br>
     */
    private String remark;


}