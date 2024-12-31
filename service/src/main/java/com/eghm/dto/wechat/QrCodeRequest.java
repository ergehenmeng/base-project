package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class QrCodeRequest {

    @ApiModelProperty("链接地址")
    @NotBlank(message = "链接地址不能为空")
    private String pageUrl;

    @ApiModelProperty("页面参数")
    private String query;

    @ApiModelProperty("有效日期")
    private Integer validDay = 1;

}
