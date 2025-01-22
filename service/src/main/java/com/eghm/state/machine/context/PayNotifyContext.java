package com.eghm.state.machine.context;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
import com.eghm.pay.enums.TradeType;
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
    private String tradeNo;

    @ApiModelProperty("支付方式")
    private TradeType tradeType;

    @ApiModelProperty("支付金额")
    private Integer amount;

    @ApiModelProperty("支付成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("产品类型")
    private ProductType productType;

    @ApiModelProperty("事件")
    private IEvent event;
}
