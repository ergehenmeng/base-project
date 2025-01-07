package com.eghm.dto.member;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Data
public class SendEmailAuthCodeDTO {

    @Schema(description = "手机号或邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "邮箱格式错误")
    private String email;

    @Schema(description = "短信验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "短信验证码不能为空")
    private String smsCode;

    @Assign
    @Schema(description = "用户ID", hidden = true)
    private Long memberId;

}
