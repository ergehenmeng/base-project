package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/1/15
 */

@Data
public class MaOpenLoginDTO {

    @ApiModelProperty(value = "openId", required = true)
    @NotBlank(message = "授权openId不能为空")
    private String openId;
}
