package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_task_log")
public class SysTaskLog {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "定时任务bean名称")
    private String beanName;

    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "方法入参")
    private String args;

    @Schema(description = "执行结果 0:失败 1:成功")
    private Boolean state;

    @Schema(description = "执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "任务耗时,单位:ms")
    private Long elapsedTime;

    @Schema(description = "执行任务的机器ip")
    private String ip;

    @Schema(description = "执行错误时的信息")
    private String errorMsg;

}