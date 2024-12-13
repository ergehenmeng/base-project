package com.eghm.pay.vo;


import com.eghm.pay.enums.TradeState;
import com.eghm.pay.enums.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
public class PayOrderVO {

    @ApiModelProperty("支付id: 微信openId")
    private String payerId;

    @ApiModelProperty("交易状态")
    private TradeState tradeState;

    @ApiModelProperty("支付方式")
    private TradeType tradeType;

    @ApiModelProperty("支付金额")
    private Integer amount;

    @ApiModelProperty("附加信息")
    private String attach;

    @ApiModelProperty("支付成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("交易单号(微信或支付宝方生成)")
    private String transactionId;

}
