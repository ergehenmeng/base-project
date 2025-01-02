package com.eghm.dto.sys.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:57
 */
@Data
public class AuthSmsRequest {

    @Size(min = 4, max = 6, message = "短信验证码格式错误")
    @NotBlank(message = "短信验证码不能为空")
    @Schema(description = "短信验证码4-6位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String smsCode;

    @Schema(description = "secretId", hidden = true)
    @NotBlank(message = "验证密码ID不能为空")
    private String secretId;
}
