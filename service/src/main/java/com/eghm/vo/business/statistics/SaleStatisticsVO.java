package com.eghm.vo.business.statistics;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/2
 */

@Data
public class SaleStatisticsVO {

    @Schema(description = "销售额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "商品图片")
    private String productImg;

    @Schema(description = "商品名称")
    private String productName;
}
