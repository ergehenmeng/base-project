package com.eghm.application.shared.dto.business.news.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsConfigEditRequest extends NewsConfigAddRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;
}
