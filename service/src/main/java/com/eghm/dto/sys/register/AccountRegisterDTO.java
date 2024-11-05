package com.eghm.dto.sys.register;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author 二哥很猛
 * @since 2024/10/28
 */

@Data
public class AccountRegisterDTO {

    @ApiModelProperty(value = "账号", required = true)
    @Length(min = 6, max = 16, message = "账号长度6-16位")
    @NotEmpty(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "密码(8~20英文,字母和@#&_)", required = true)
    @Password
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    @ApiModelProperty(value = "邀请码(非必填)")
    private String inviteCode;

    @Assign
    @ApiModelProperty(value = "注册渠道(ANDROID,IOS,PC,H5)", hidden = true)
    private String channel;

    @Assign
    @ApiModelProperty(value = "注册ip", hidden = true)
    private String ip;
}
