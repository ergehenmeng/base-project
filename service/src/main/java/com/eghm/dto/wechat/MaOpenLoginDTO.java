package com.eghm.dto.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/15
 */

@Data
public class MaOpenLoginDTO {

    @Schema(description = "openId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "授权openId不能为空")
    private String openId;
}
