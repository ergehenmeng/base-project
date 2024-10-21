package com.eghm.dto.login;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2019/8/19 16:57
 */
@Data
public class SmsLoginRequest {

    @Size(min = 4, max = 6, message = "短信验证码格式错误")
    @NotBlank(message = "短信验证码不能为空")
    @ApiModelProperty(value = "短信验证码4-6位", required = true)
    private String smsCode;

    @Mobile
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "ip", hidden = true)
    @Assign
    private String ip;
}
