package com.eghm.dto.sys.login;

import com.eghm.validation.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2021/12/26 19:22
 */
@Data
public class SetPasswordDTO {

    @Schema(description = "请求唯一ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "请求ID不能为空")
    private String requestId;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password
    private String password;
}
