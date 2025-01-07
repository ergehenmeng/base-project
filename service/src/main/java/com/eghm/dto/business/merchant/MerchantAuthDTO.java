package com.eghm.dto.business.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantAuthDTO {

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "授权码不能为空")
    private String authCode;

    @Schema(description = "授权手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "授权手机号不能为空")
    private String mobile;

    @Schema(description = "授权openId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "openId不能为空")
    private String openId;

}
