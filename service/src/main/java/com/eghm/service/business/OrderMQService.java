package com.eghm.service.business;

import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;

/**
 * @author 二哥很猛
 * @since 2022/7/28
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
     * 1. 添加评论信息 (延迟)
     * 2. 实时消息 (分账)
     *
     * @param exchangeQueue 队列类型
     * @param orderNo       订单信息
     */
    void sendOrderCompleteMessage(ExchangeQueue exchangeQueue, String orderNo);

}
