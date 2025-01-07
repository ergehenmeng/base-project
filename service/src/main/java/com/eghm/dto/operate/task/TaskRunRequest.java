package com.eghm.dto.operate.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
public class TaskRunRequest {

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "任务id不能为空")
    private Long id;

    @Schema(description = "任务参数")
    private String args;
}
