package com.eghm.dto.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
public class TaskRunRequest {

    @ApiModelProperty("任务id")
    @NotNull(message = "任务id不能为空")
    private Long id;

    @ApiModelProperty("任务参数")
    private String args;
}
