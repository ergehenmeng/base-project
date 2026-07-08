package com.eghm.pay.dto;

import lombok.Data;

/**
 * 第三方支付回调解析后的标准消息.
 *
 * @author 二哥很猛
 */
@Data
public class PayNotifyMessage {

    /**
     * 第三方回调通知id.
     */
    private String notifyId;

    /**
     * 商户支付单号.
     */
    private String tradeNo;

    /**
     * 商户退款单号.
     */
    private String refundNo;

    /**
     * 回调业务参数.
     */
    private String params;

    public static PayNotifyMessage pay(String notifyId, String tradeNo, String params) {
        PayNotifyMessage message = new PayNotifyMessage();
        message.setNotifyId(notifyId);
        message.setTradeNo(tradeNo);
        message.setParams(params);
        return message;
    }

    public static PayNotifyMessage refund(String notifyId, String tradeNo, String refundNo, String params) {
        PayNotifyMessage message = pay(notifyId, tradeNo, params);
        message.setRefundNo(refundNo);
        return message;
    }
}
