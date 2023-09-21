package com.eghm.dto.business.merchant;

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

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String memberName;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @ApiModelProperty(value = "验证码", required = true)
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
}
