package com.eghm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/6/9
 */

@Data
public class RecommendDTO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "推荐状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "推荐状态不能为空")
    private Boolean recommend;
}
