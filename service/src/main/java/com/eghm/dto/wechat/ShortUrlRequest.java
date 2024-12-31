package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class ShortUrlRequest {

    @ApiModelProperty("链接地址")
    @NotBlank(message = "链接地址不能为空")
    private String pageUrl;

    @ApiModelProperty("页面标题")
    private String pageTitle;

    @ApiModelProperty("是否持久有效")
    private Boolean persistent = false;
}
