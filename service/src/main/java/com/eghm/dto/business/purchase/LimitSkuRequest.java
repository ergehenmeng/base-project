package com.eghm.dto.business.purchase;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitSkuRequest {

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品id不能为空")
    private Long itemId;

    @Schema(description = "skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @Schema(description = "限时价", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "限时价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer discountPrice;
}
