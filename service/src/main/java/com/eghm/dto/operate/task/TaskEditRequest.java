package com.eghm.dto.operate.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author 二哥很猛
 * @since 2019/9/12 18:00
 */
@Data
public class TaskEditRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "定时任务名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "定时任务名称不能为空")
    private String title;

    @Schema(description = "cron表达式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    @Schema(description = "方法参数")
    private String args;

    @Schema(description = "报警邮箱")
    private String alarmEmail;

    @Schema(description = "锁持有时间,毫秒")
    @NotNull(message = "锁持有时间不能为空")
    @Range(min = 30000, max = 3600000, message = "锁持有时间应在30000~3600000ms之间")
    private Long lockTime;

    @Schema(description = "状态 0:未开启 1:已开启", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "任务状态不能为空")
    private Boolean state;

    @Schema(description = "备注信息")
    private String remark;
}
