package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wyb
 * @date 2022/6/12 18:47
 */
@Getter
@AllArgsConstructor
public enum RabbitQueue {

    /**
     * 订单未支付时取消队列 延迟队列
     */
    ORDER_PAY_EXPIRE("order_pay_expire_exchange", "order_pay_expire_queue", ""),

    /**
     * 订单发货后自动完成 延迟队列
     */
    ORDER_COMPLETE("order_complete_exchange", "order_complete_queue", ""),

    /**
     * 创建订单队列
     */
    ORDER_CREATE("order_create_exchange", "order_create_queue", ""),

    /**
     * 死信队列
     */
    DEAD_LETTER("dead_letter_exchange", "dead_letter_queue", ""),

    ;

    /**
     * mq交换机名称
     */
    private final String exchange;

    /**
     * mq队列名称
     */
    private final String queue;

    /**
     * 路由key
     */
    private final String routingKey;

}
