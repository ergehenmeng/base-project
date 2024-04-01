package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/4/1
 */

@Data
public class JsTicketDTO {

    @ApiModelProperty("url")
    @NotBlank(message = "签名url不能为空")
    private String url;
}
