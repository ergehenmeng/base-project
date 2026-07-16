package com.eghm.platform.iam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/10/20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthConfigEditRequest extends AuthConfigAddRequest {

    @Schema(description = "id不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

}
