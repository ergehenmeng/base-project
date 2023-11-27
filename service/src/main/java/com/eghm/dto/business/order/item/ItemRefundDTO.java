package com.eghm.dto.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
public class ItemRefundDTO {

    @ApiModelProperty("退款订单ID")
    private Long itemOrderId;

    @ApiModelProperty("退款数量")
    private Integer num;
}
