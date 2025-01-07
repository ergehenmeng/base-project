package com.eghm.dto.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/4/1
 */

@Data
public class JsTicketDTO {

    @Schema(description = "url")
    @NotBlank(message = "签名url不能为空")
    private String url;
}
