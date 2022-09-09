package com.eghm.service.business.handler;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface PayNotifyHandler {

    /**
     * 支付回调处理
     * @param orderNo 订单编号
     * @param outTradeNo 交易流水号
     */
    void process(String orderNo, String outTradeNo);
}
