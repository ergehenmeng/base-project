package com.eghm.model.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @date 2022/1/28 17:26
 */
@Data
public class LoginRequest {

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    private String pwd;

    @ApiModelProperty("验证码Key")
    @NotNull(message = "key不能为空")
    private String key;

    @ApiModelProperty("验证码")
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
}
