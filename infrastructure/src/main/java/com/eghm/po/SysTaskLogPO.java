package com.eghm.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_task_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysTaskLogPO {
    /** id主键 */
    private Long id;

    /** 定时任务bean名称 */
    private String beanName;

    /** 方法名称 */
    private String methodName;

    /** 方法入参 */
    private String args;

    /** 执行结果 0:失败 1:成功 */
    private Boolean state;

    /** 执行时间 */
    private LocalDateTime startTime;

    /** 任务耗时,单位:ms */
    private Long elapsedTime;

    /** 执行任务的机器ip */
    private String ip;

    /** 执行错误时的信息 */
    private String errorMsg;

}

