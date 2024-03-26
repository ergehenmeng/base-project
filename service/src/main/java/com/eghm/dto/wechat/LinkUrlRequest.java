package com.eghm.dto.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class LinkUrlRequest {

    @ApiModelProperty("链接地址")
    private String pageUrl;

    @ApiModelProperty("页面参数")
    private String query;

    @ApiModelProperty("有效日期")
    private Integer validDay = 30;
}
