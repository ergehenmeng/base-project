package com.eghm.service.pay.response;


import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    /**
     * 银行类型
     */
    @JsonProperty("bank_type")
    private String bankType;

    /**
     * 附件信息
     */
    private String attach;

    /**
     * 支付金额信息
     */
    @JsonProperty("amount")
    private PayAmount payAmount;

    /**
     * 付款人信息
     */
    private Payer payer;

    /**
     * 支付完成时间
     */
    @JsonProperty("success_time")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "GMT+08")
    private LocalDateTime successTime;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PayAmount {

        /**
         * 订单总金额
         */
        private Integer total;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Payer {

        @JsonProperty("openid")
        private String openId;
    }

}
