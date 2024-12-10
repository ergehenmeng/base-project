package com.eghm.vo.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/31
 */
@Data
public class ItemUnShippedOrderResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品封面图")
    private String coverUrl;
}
