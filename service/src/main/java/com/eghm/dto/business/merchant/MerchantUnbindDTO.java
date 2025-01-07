package com.eghm.dto.business.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantUnbindDTO {

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 6, message = "验证码格式错误")
    private String smsCode;

}
