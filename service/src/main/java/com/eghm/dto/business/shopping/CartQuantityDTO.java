package com.eghm.dto.business.shopping;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 购物车商品数量更新
 *
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class CartQuantityDTO {

    @Schema(description = "购物车商品", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "新数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 99, message = "商品数量应为1~99")
    private Integer quantity;
}
