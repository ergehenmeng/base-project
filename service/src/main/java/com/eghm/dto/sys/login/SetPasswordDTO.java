package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @ApiModelProperty(value = "请求唯一ID", required = true)
    @NotBlank(message = "请求ID不能为空")
    private String requestId;

    @ApiModelProperty(value = "密码", required = true)
    @Password
    private String password;
}
