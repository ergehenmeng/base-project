package com.eghm.service.pay.response;


import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    @ApiModelProperty("支付id: 微信openId")
    private String payId;

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
