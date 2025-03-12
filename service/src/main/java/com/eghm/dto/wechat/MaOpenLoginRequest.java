package com.eghm.dto.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * 管理后台用户登录
 *
 * @author 二哥很猛
 * @since 2024/1/15
 */

@Data
public class MaOpenLoginRequest {

    @Schema(description = "授权手机号code")
    @NotBlank(message = "授权信息不能为空")
    private String code;
}
