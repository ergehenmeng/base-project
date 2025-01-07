package com.eghm.dto.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2021/12/18 16:31
 */
@Data
public class MaLoginDTO {

    @Schema(description = "授权code码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "授权码不能为空")
    private String code;

    @Schema(description = "openId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "授权openId不能为空")
    private String openId;
}
