package com.eghm.model.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @ApiModelProperty(value = "请求唯一ID", required = true)
    @NotBlank(message = "请求ID不能为空")
    private String requestId;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
