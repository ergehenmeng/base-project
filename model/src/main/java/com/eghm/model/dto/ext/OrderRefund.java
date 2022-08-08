package com.eghm.model.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2022/8/8
 */
@Data
public class OrderRefund {

    @ApiModelProperty("支付订单流水号")
    private String outTradeNo;

    @ApiModelProperty("退款订单流水号")
    private String outRefundNo;
}
