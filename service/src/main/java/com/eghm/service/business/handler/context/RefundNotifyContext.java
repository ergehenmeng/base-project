package com.eghm.service.business.handler.context;

import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Data
public class RefundNotifyContext implements Context {

    @ApiModelProperty("支付流水号")
    private String outTradeNo;

    @ApiModelProperty("退款流水号")
    private String outRefundNo;

    @ApiModelProperty("本次退款金额")
    private Integer amount;

    @ApiModelProperty("退款成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

}
