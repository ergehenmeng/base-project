package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class DiscountJson {

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "优惠金额")
    private Integer discountPrice;
}
