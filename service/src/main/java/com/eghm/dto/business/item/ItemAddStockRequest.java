package com.eghm.dto.business.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemAddStockRequest {

    @Schema(description = "商品id")
    @NotNull(message = "商品id不能为空")
    private Long itemId;

    @Schema(description = "规格列表")
    @NotEmpty(message = "规格列表不能为空")
    private List<ItemStockRequest> skuList;
}
