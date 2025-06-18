package com.eghm.vo.business.limit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitSkuVO {

    @ApiModelProperty(value = "商品id")
    private Long itemId;

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "限时价")
    private Integer discountPrice;
}
