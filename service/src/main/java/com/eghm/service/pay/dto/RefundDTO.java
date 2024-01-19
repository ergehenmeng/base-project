package com.eghm.service.pay.dto;

import com.eghm.service.pay.enums.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Data
public class RefundDTO {

    @ApiModelProperty(value = "订单号", hidden = true)
    private String orderNo;

    @ApiModelProperty(value = "商户流水号(支付单号)", required = true)
    private String outTradeNo;

    @ApiModelProperty(value = "退款流水号(商户生成)", required = true)
    private String outRefundNo;

    @ApiModelProperty("退款金额")
    private Integer amount;

    @ApiModelProperty("原支付金额")
    private Integer total;

    @ApiModelProperty("交易方式")
    private TradeType tradeType;

    @ApiModelProperty("退款原因")
    private String reason;
}
