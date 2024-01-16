package com.eghm.service.pay.dto;

import com.eghm.service.pay.enums.TradeType;
import lombok.Data;

/**
 * 预支付订单对象
 *
 * @author 二哥很猛
 */
@Data
public class PrepayDTO {

    /**
     * 交易订单号
     */
    private String outTradeNo;

    /**
     * 支付方式
     */
    private TradeType tradeType;

    /**
     * 订单描述信息
     */
    private String description;

    /**
     * 付款人buyerId
     */
    private String buyerId;

    /**
     * 付款金额 单位:分
     */
    private Integer amount;

    /**
     * 附加信息
     */
    private String attach;
}