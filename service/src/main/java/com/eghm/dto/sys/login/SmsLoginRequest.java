package com.eghm.dto.sys.login;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:57
 */
@Data
public class SmsLoginRequest {

    @Size(min = 4, max = 6, message = "短信验证码格式错误")
    @NotBlank(message = "短信验证码不能为空")
    @Schema(description = "短信验证码4-6位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String smsCode;

    @Mobile
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Schema(description = "ip", hidden = true)
    @Assign
    private String ip;
}
