package com.eghm.service.pay.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PrepayRequest extends BaseRequest {

    @JsonProperty("appid")
    private String appId;

    private String description;

    @JsonProperty("out_trade_no")
    private String orderNo;

    @JsonProperty("time_expire")
    private String expireTime;

    private String attach;

    @JsonProperty("notify_url")
    private String notifyUrl;

    @JsonProperty("amount")
    private PayAmount payAmount;

    private Payer payer;

    public void setAmount(Integer amount) {
        this.payAmount = new PayAmount(amount);
    }

    public void setOpenId(String openId) {
        this.payer = new Payer(openId);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PayAmount {

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