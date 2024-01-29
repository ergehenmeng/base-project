package com.eghm.dto.business.purchase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class PurchaseItemRequest {

    @ApiModelProperty("商品id")
    @NotNull(message = "请选择商品")
    private Long itemId;

    @ApiModelProperty("规格优惠列表")
    @NotEmpty(message = "请填写优惠金额")
    private List<LimitSkuRequest> skuList;
}
