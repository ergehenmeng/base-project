package com.eghm.dto.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemAddStockRequest {

    @ApiModelProperty(value = "商品id")
    @NotNull(message = "商品id不能为空")
    private Long itemId;

    @ApiModelProperty(value = "规格列表")
    @NotEmpty(message = "规格列表不能为空")
    private List<ItemStockRequest> skuList;
}
