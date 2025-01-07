package com.eghm.dto.business.line.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/8/30
 */
@Data
public class LineConfigQueryRequest {

    @Schema(description = "月份 yyyy-MM", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "月份不能为空")
    private String month;

    @Schema(description = "线路id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "线路id不能为空")
    private Long lineId;
}
