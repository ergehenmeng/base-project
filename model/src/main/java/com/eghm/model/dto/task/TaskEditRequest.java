package com.eghm.model.dto.task;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/9/12 18:00
 */
@Data
public class TaskEditRequest implements Serializable {

    private static final long serialVersionUID = -2227192870653756523L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 表达式
     */
    @ApiModelProperty(value = "cron表达式", required = true)
    @NotNull(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 开启获取关闭
     */
    @ApiModelProperty(value = "状态 0:未开启 1:已开启", required = true)
    @NotNull(message = "任务状态不能为空")
    private Boolean state;

    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remark;
}
