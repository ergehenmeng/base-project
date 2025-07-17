package com.eghm.dto.sys.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/7/18 17:26
 */
@Data
public class TotpCheckRequest {

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "序列号不能为空")
    private String uuid;

    @Schema(description = "校验码", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer verifyCode;
}
