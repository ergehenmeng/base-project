package com.eghm.vo.business.limit;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty("销售价")
    @NotNull(message = "销售价不能为空")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("限时价")
    @NotNull(message = "限时价不能为空")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer discountPrice;
}
