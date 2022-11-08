package com.eghm.model.dto.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/9/12 18:00
 */
@Data
public class TaskEditRequest implements Serializable {

    private static final long serialVersionUID = -2227192870653756523L;

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "cron表达式", required = true)
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @ApiModelProperty(value = "状态 0:未开启 1:已开启", required = true)
    @NotNull(message = "任务状态不能为空")
    private Boolean state;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
