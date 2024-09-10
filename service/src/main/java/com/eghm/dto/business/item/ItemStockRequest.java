package com.eghm.dto.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemStockRequest {

    @ApiModelProperty("sku_id")
    @NotNull(message = "请选择规格")
    private Long id;

    @ApiModelProperty("增加的库存数量")
    @Min(value = 0, message = "库存数量不能小于0")
    @NotNull(message = "库存数量不能为空")
    private Integer num;
}
