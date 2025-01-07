package com.eghm.vo.business.limit;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitSkuResponse {

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "sku数量")
    private Integer skuSize;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @Schema(description = "限时价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer discountPrice;
}
