package com.eghm.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class SysTaskLogResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("定时任务bean名称")
    private String beanName;

    @ApiModelProperty("方法名称")
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
    private String ip;

}