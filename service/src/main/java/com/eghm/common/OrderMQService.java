package com.eghm.common;

import com.eghm.dto.ext.AsyncKey;
import com.eghm.dto.ext.RefundAudit;
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

    /**
     * 发送订单退款消息(仅退款) 延迟审核消息 24小时
     *
     * @param exchangeQueue 队列类型
     * @param audit         退款信息
     */
    void sendRefundAuditMessage(ExchangeQueue exchangeQueue, RefundAudit audit);

    /**
     * 发送订单退款消息(退货退款) 延迟审核消息 7天
     *
     * @param exchangeQueue 队列类型
     * @param audit         退款信息
     */
    void sendReturnRefundAuditMessage(ExchangeQueue exchangeQueue, RefundAudit audit);
}
