package com.eghm.service.pay.response;


import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {

    /**
     * 应用id
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 直连商户号
     */
    @JsonProperty("mchid")
    private String merchantId;

    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String orderNo;

    /**
     * 微信支付订单号
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    /**
     * 付款方式
     */
    @JsonProperty("trade_type")
    private TradeType tradeType;

    /**
     * 订单状态
     */
    @JsonProperty("trade_state")
    private TradeState tradeState;


}
