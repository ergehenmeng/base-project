package com.eghm.vo.business.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupSkuVO {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "拼团价")
    private Integer discountPrice;
}
