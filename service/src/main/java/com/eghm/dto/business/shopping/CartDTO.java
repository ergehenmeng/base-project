package com.eghm.dto.business.shopping;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class CartDTO {

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @Schema(description = "商品规格id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规格不能为空")
    private Long skuId;

    @Schema(description = "商品售价(冗余)")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer salePrice;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 99, message = "数量必须1~99之间")
    private Integer quantity;

    @Assign
    @Schema(description = "用户ID", hidden = true)
    private Long memberId;
}
