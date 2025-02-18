package com.eghm.dto.business.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemStockRequest {

    @Schema(description = "sku_id")
    @NotNull(message = "请选择规格")
    private Long id;

    @Schema(description = "增加的库存数量")
    @Min(value = 0, message = "库存数量不能小于0")
    @NotNull(message = "库存数量不能为空")
    private Integer num;
}
