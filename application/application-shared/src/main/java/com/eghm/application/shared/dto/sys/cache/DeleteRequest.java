package com.eghm.application.shared.dto.sys.cache;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/12/25
 */
@Data
public class DeleteRequest {

    @Schema(description = "key", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "key不能为空")
    private String key;
}
