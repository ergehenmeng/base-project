package com.eghm.service.business.handler.context;

import com.eghm.service.pay.enums.TradeType;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class PayNotifyContext implements Context {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("支付流水号")
    private String outTradeNo;

    @ApiModelProperty("支付方式")
    private TradeType tradeType;

    @ApiModelProperty("支付金额")
    private Integer amount;

    @ApiModelProperty("支付成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("源状态")
    private Integer from;

}
