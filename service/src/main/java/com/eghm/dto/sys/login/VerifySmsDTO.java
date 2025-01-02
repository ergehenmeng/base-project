package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2021/12/26 19:40
 */
@Data
public class VerifySmsDTO {

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Mobile
    private String mobile;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String smsCode;
}
