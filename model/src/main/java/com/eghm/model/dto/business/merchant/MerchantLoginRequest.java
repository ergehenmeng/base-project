package com.eghm.model.dto.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
public class MerchantLoginRequest {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @ApiModelProperty("验证码")
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
}
