package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_task_log")
public class SysTaskLog extends BaseEntity {

    @ApiModelProperty("定时任务bean名称")
    private String beanName;

    @ApiModelProperty("方法名称")
    private String methodName;

    @ApiModelProperty("方法入参")
    private String args;

    @ApiModelProperty("执行结果 0:失败 1:成功")
    private Boolean state;

    @ApiModelProperty("执行结束时间")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long elapsedTime;

    @ApiModelProperty("执行任务的机器ip")
    private String ip;

    @ApiModelProperty("执行错误时的信息")
    private String errorMsg;

}