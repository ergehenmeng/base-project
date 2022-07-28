package com.eghm.common.enums;

import com.eghm.common.constant.QueueConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/6/12 18:47
 */
@Getter
@AllArgsConstructor
public enum RabbitQueue {

    /**
     * 订单未支付时取消队列 延迟队列
     */
    ORDER_PAY_EXPIRE("order_pay_expire_exchange", QueueConstant.ORDER_PAY_EXPIRE_QUEUE, ""),

    /**
     * 订单发货后自动完成 延迟队列
     */
    ORDER_COMPLETE("order_complete_exchange", QueueConstant.ORDER_COMPLETE_QUEUE, ""),

    /**
     * 创建订单队列
     */
    ORDER_CREATE("order_create_exchange", QueueConstant.ORDER_CREATE_QUEUE, ""),

    /**
     * 优惠券领取
     */
    COUPON_RECEIVE("coupon_receive_exchange", QueueConstant.COUPON_RECEIVE_QUEUE, ""),

    /**
     * 死信队列
     */
    DEAD_LETTER("dead_letter_exchange", QueueConstant.DEAD_LETTER_QUEUE, ""),

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
