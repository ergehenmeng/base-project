package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("定时任务bean名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String beanName;

    @ApiModelProperty("方法名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String methodName;

    @ApiModelProperty("方法入参")
    private String args;

    @ApiModelProperty("执行结果 0:失败 1:成功")
    private Boolean state;

    @ApiModelProperty("执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty("任务耗时,单位:ms")
    private Long elapsedTime;

    @ApiModelProperty("执行任务的机器ip")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String ip;

    @ApiModelProperty("执行错误时的信息")
    private String errorMsg;

}