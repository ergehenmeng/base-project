package com.eghm.dto.member.register;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户注册 密码注册(3步)或验证码注册(2)
 *
 * @author 二哥很猛
 * @since 2019/9/3 16:32
 */
@Data
public class RegisterMemberDTO {

    @Mobile
    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Size(min = 4, max = 6, message = "验证码格式错误")
    @Schema(description = "短信验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    @Schema(description = "注册邀请码(非必填)")
    private String inviteCode;

    @Schema(description = "注册渠道", hidden = true)
    @Assign
    private String channel;

    @Schema(description = "注册ip", hidden = true)
    @Assign
    private String ip;
}
