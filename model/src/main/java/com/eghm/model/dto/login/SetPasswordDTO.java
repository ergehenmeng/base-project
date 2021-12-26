package com.eghm.model.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @date 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @ApiModelProperty(value = "请求唯一ID", required = true)
    @NotNull(message = "请求ID不能为空")
    private String requestId;

    @ApiModelProperty(value = "新密码", required = true)
    @NotNull(message = "密码不能为空")
    private String password;
}
