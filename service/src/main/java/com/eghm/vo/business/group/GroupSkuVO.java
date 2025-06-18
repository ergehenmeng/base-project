package com.eghm.vo.business.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
public class GroupSkuVO {

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "销售价")
    private Integer salePrice;

    @Schema(description = "拼团价")
    private Integer discountPrice;
}
