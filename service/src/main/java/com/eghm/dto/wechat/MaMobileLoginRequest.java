package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 管理后台用户登录
 *
 * @author 二哥很猛
 * @since 2025/3/12
 */

@Data
public class MaMobileLoginRequest {

    @ApiModelProperty("授权手机号code")
    @NotBlank(message = "授权信息不能为空")
    private String code;

    @ApiModelProperty("openId")
    @NotBlank(message = "授权openId不能为空")
    private String openId;
}
