package com.eghm.dto.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantUnbindDTO {

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 6, message = "验证码格式错误")
    private String smsCode;

}
