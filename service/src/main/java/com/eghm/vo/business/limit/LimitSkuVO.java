package com.eghm.vo.business.limit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitSkuVO {

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "限时价")
    private Integer discountPrice;
}
