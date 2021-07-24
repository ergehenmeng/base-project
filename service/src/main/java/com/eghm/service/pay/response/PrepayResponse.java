package com.eghm.service.pay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepayResponse {

    /**
     * 预支付id
     */
    @JsonProperty("prepay_id")
    private String prepayId;

    /**
     * 订单号
     */
    private String orderNo;
}