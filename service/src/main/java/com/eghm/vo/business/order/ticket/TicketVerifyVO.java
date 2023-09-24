package com.eghm.vo.business.order.ticket;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/9/24
 */
@Data
public class TicketVerifyVO {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("商户ID")
    private Long merchantId;
}
