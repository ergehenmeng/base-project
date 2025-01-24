package com.eghm.dto.sys.register;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Password;
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

    @Schema(description = "密码(8~20英文,字母和@#&_)", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password
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
