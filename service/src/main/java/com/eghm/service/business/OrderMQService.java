package com.eghm.service.business;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
public interface OrderMQService {

    /**
     * 发送订单过期消息
     * @param orderNo 订单编号
     */
    void sendOrderExpireMessage(String orderNo);
}
