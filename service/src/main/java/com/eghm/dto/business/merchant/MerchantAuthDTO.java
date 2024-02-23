package com.eghm.dto.business.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantAuthDTO {

    @ApiModelProperty(value = "授权码", required = true)
    @NotBlank(message = "授权码不能为空")
    private String authCode;

    @ApiModelProperty(value = "授权手机号", required = true)
    @NotBlank(message = "授权手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "授权openId", required = true)
    @NotBlank(message = "openId不能为空")
    private String openId;

}
