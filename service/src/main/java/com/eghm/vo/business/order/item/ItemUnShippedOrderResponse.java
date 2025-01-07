package com.eghm.vo.business.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class ItemUnShippedOrderResponse {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品封面图")
    private String coverUrl;
}
