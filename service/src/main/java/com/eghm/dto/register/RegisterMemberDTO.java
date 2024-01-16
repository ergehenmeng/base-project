package com.eghm.dto.register;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户注册 密码注册(3步)或验证码注册(2)
 *
 * @author 二哥很猛
 * @date 2019/9/3 16:32
 */
@Data
public class RegisterMemberDTO {

    @Mobile
    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @Size(min = 4, max = 6, message = "验证码格式错误")
    @ApiModelProperty(value = "短信验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    @ApiModelProperty(value = "注册邀请码(非必填)")
    private String inviteCode;

    @ApiModelProperty(value = "注册渠道", hidden = true)
    @Assign
    private String channel;

    @ApiModelProperty(value = "注册ip", hidden = true)
    @Assign
    private String ip;
}
