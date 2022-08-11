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
     * 门票订单队列
     */
    TICKET_ORDER("ticket_order_exchange", QueueConstant.TICKET_ORDER_QUEUE, ""),

    /**
     * 餐饮订单队列
     */
    VOUCHER_ORDER("voucher_order_exchange", QueueConstant.VOUCHER_ORDER_QUEUE, ""),

    /**
     * 民宿订单队列
     */
    HOMESTAY_ORDER("homestay_order_exchange", QueueConstant.HOMESTAY_ORDER_QUEUE, ""),

    /**
     * 商品订单队列
     */
    PRODUCT_ORDER("product_order_exchange", QueueConstant.PRODUCT_ORDER_QUEUE, ""),

    /**
     * 优惠券领取
     */
    COUPON_RECEIVE("coupon_receive_exchange", QueueConstant.COUPON_RECEIVE_QUEUE, ""),

    /**
     * 死信队列
     */
    DEAD_LETTER("dead_letter_exchange", QueueConstant.DEAD_LETTER_QUEUE, ""),

    /**
     * 管理后台日志队列
     */
    MANAGE_LOG("manage_log_exchange", QueueConstant.MANAGE_LOG_QUEUE, ""),

    /**
     * 管理后台日志队列
     */
    LOGIN_LOG("login_log_exchange", QueueConstant.LOGIN_LOG_QUEUE, ""),

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
