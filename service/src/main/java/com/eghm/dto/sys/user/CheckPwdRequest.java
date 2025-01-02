package com.eghm.dto.sys.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2022/9/17
 */
@Data
public class CheckPwdRequest {

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
