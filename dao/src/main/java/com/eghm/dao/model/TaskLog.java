package com.eghm.dao.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskLog extends BaseEntity {

    /**
     * 任务nid<br>
     * 表 : task_log<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 定时任务bean名称<br>
     * 表 : task_log<br>
     * 对应字段 : bean_name<br>
     */
    private String beanName;

    /**
     * 执行结果 0:失败 1:成功<br>
     * 表 : task_log<br>
     * 对应字段 : state<br>
     */
    private Boolean state;

    /**
     * 开始执行时间<br>
     * 表 : task_log<br>
     * 对应字段 : start_time<br>
     */
    private Date startTime;

    /**
     * 执行结束时间<br>
     * 表 : task_log<br>
     * 对应字段 : elapsed_time<br>
     */
    private Long elapsedTime;

    /**
     * 执行任务的机器ip<br>
     * 表 : task_log<br>
     * 对应字段 : ip<br>
     */
    private String ip;

    /**
     * 执行错误时的信息<br>
     * 表 : task_log<br>
     * 对应字段 : error_msg<br>
     */
    private String errorMsg;

    private static final long serialVersionUID = 1L;

}