package com.eghm.application.shared.dto.sys.login;

import com.eghm.application.shared.convertor.RsaPasswordDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:26
 */
@Data
public class LoginRequest {

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "账号不能为空")
    private String userName;

    @Schema(description = "密码(rsa加密)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Expose(serialize = false)
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    private String pwd;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
