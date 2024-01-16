package com.eghm.service.business;

import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
public interface OrderMQService {

    /**
     * 发送订单过期消息
     *
     * @param exchangeQueue 队列类型
     * @param orderNo       订单编号
     */
    void sendOrderExpireMessage(ExchangeQueue exchangeQueue, String orderNo);

    /**
     * 发送创建订单的消息
     *
     * @param exchangeQueue 队列类型
     * @param context       下单信息
     */
    void sendOrderCreateMessage(ExchangeQueue exchangeQueue, AsyncKey context);

    /**
     * 发送订单完成消息
     *
     * @param exchangeQueue 队列类型
     * @param orderNo       订单信息
     */
    void sendOrderCompleteMessage(ExchangeQueue exchangeQueue, String orderNo);
}
