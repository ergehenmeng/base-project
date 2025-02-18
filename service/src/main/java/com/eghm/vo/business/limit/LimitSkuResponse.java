package com.eghm.vo.business.limit;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitSkuResponse {

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("sku数量")
    private Integer skuSize;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty("销售价")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer salePrice;

    @ApiModelProperty("限时价")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer discountPrice;
}
