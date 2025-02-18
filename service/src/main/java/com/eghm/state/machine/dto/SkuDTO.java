package com.eghm.state.machine.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wyb
 * @since 2023/6/14
 */
@Data
public class SkuDTO {

    @Schema(description = "商品id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99")
    @Schema(description = "商品数量,最大99", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer num;

    @Schema(description = "商品skuId", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "规格信息不能为空")
    private Long skuId;
}
