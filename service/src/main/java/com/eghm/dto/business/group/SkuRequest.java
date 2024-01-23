package com.eghm.dto.business.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class SkuRequest {

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("销售价")
    private Integer salePrice;

    @ApiModelProperty("拼团价")
    private Integer groupPrice;
}
