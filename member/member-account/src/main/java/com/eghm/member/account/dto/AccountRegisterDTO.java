package com.eghm.member.account.dto;

import com.eghm.foundation.core.annotation.Assign;
import com.eghm.foundation.core.convertor.RsaPasswordDeserializer;
import com.eghm.foundation.core.validation.annotation.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2024/10/28
 */

@Data
public class AccountRegisterDTO {

    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 6, max = 16, message = "账号长度6-16位")
    @NotEmpty(message = "账号不能为空")
    private String account;

    @Schema(description = "密码(8~20英文,字母和@#&_)rsa加密", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password
    @JsonDeserialize(using = RsaPasswordDeserializer.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @Schema(description = "邀请码(非必填)")
    private String inviteCode;

    @Assign
    @Schema(description = "注册渠道(ANDROID,IOS,PC,H5)", hidden = true)
    private String channel;

    @Assign
    @Schema(description = "注册ip", hidden = true)
    private String ip;
}
