package com.eghm.dto.wechat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class LinkUrlRequest {

    @Schema(description = "链接地址")
    private String pageUrl;

    @Schema(description = "页面参数")
    private String query;

    @Schema(description = "有效日期")
    private Integer validDay = 30;
}
