package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @date 2021/12/18 16:31
 */
@Data
public class MaLoginDTO {

    @ApiModelProperty(value = "授权code码", required = true)
    @NotBlank(message = "授权码不能为空")
    private String code;

    @ApiModelProperty("openId")
    @NotBlank(message = "授权openId不能为空")
    private String openId;
}
