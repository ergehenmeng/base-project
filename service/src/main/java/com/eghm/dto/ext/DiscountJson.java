package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class DiscountJson {

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("销售价")
    private Integer salePrice;

    @ApiModelProperty("优惠金额")
    private Integer discountPrice;
}
