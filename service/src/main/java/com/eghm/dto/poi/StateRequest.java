package com.eghm.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/12/20
 */

@Data
public class StateRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "状态 true:启用 false:禁用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean state;
}
