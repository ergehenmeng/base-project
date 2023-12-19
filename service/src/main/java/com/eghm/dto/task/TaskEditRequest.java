package com.eghm.dto.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2019/9/12 18:00
 */
@Data
public class TaskEditRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "cron表达式", required = true)
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @ApiModelProperty(value = "方法参数")
    private String args;

    @ApiModelProperty(value = "报警邮箱")
    private String alarmEmail;

    @ApiModelProperty("锁持有时间,毫秒")
    @NotNull(message = "锁持有时间不能为空")
    @Range(min = 30000, max = 3600000, message = "锁持有时间应在30000~3600000毫秒之间")
    private Long lockTime;

    @ApiModelProperty(value = "状态 0:未开启 1:已开启", required = true)
    @NotNull(message = "任务状态不能为空")
    private Boolean state;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
