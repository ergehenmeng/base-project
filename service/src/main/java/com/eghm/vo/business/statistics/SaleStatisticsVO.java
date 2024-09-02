package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/2
 */

@Data
public class SaleStatisticsVO {

    @ApiModelProperty("销售额")
    private Integer amount;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品名称")
    private String productName;
}
