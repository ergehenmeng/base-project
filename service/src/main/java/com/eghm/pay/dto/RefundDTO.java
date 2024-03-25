package com.eghm.pay.dto;

import com.eghm.pay.enums.TradeType;
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

    @ApiModelProperty(value = "商户支付流水号", required = true)
    private String tradeNo;

    @ApiModelProperty(value = "商户退款流水号", required = true)
    private String refundNo;

    @ApiModelProperty("退款金额")
    private Integer amount;

    @ApiModelProperty("原支付金额")
    private Integer total;

    @ApiModelProperty("交易方式")
    private TradeType tradeType;

    @ApiModelProperty("退款原因")
    private String reason;
}
