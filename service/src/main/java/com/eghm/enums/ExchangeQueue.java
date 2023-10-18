package com.eghm.enums;

import com.eghm.constant.QueueConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用于定义RabbitMQ的枚举, 注意:routingKey不支持多配置
 * @author 二哥很猛
 * @date 2022/6/12 18:47
 */
@Getter
@AllArgsConstructor
public enum ExchangeQueue {

    /**
     * 零售订单未支付时取消队列 延迟队列
     */
    ITEM_PAY_EXPIRE("item_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_PAY_EXPIRE_QUEUE}, "",true),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    TICKET_PAY_EXPIRE("ticket_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_PAY_EXPIRE_QUEUE}, "",true),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    HOMESTAY_PAY_EXPIRE("homestay_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.HOMESTAY_PAY_EXPIRE_QUEUE}, "",true),

    /**
     * 餐饮券订单未支付时取消队列 延迟队列
     */
    RESTAURANT_PAY_EXPIRE("restaurant_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.RESTAURANT_PAY_EXPIRE_QUEUE}, "",true),

    /**
     * 线路订单未支付时取消队列 延迟队列
     */
    LINE_PAY_EXPIRE("line_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LINE_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 商品订单发货后自动完成 延迟队列
     */
    ITEM_COMPLETE("item_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_COMPLETE_QUEUE}, "", false),

    /**
     * 门票自动完成 延迟队列
     */
    TICKET_COMPLETE("ticket_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_COMPLETE_QUEUE}, "", false),

    /**
     * 线路自动完成 延迟队列
     */
    LINE_COMPLETE("line_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LINE_COMPLETE_QUEUE}, "", false),

    /**
     * 餐饮自动完成 延迟队列
     */
    RESTAURANT_COMPLETE("restaurant_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.RESTAURANT_COMPLETE_QUEUE}, "", false),

    /**
     * 民宿自动完成 延迟队列
     */
    HOMESTAY_COMPLETE("homestay_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.HOMESTAY_COMPLETE_QUEUE}, "", false),

    /**
     * 门票订单队列
     */
    TICKET_ORDER("ticket_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_ORDER_QUEUE}, "", false),

    /**
     * 餐饮订单队列
     */
    RESTAURANT_ORDER("restaurant_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.RESTAURANT_ORDER_QUEUE}, "", false),

    /**
     * 民宿订单队列
     */
    HOMESTAY_ORDER("homestay_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.HOMESTAY_ORDER_QUEUE}, "", false),

    /**
     * 商品订单队列
     */
    ITEM_ORDER("line_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_ORDER_QUEUE}, "", false),

    /**
     * 线路订单队列
     */
    LINE_ORDER("line_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LINE_ORDER_QUEUE}, "", false),

    /**
     * 优惠券领取
     */
    COUPON_RECEIVE("coupon_receive_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.COUPON_RECEIVE_QUEUE}, "", false),

    /**
     * 管理后台操作日志队列
     */
    MANAGE_OP_LOG("manage_op_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.MANAGE_OP_LOG_QUEUE}, "", false),

    /**
     * 移动端用户登录日志队列
     */
    LOGIN_LOG("login_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LOGIN_LOG_QUEUE}, "", false),

    /**
     * 移动端异常日志
     */
    WEBAPP_LOG("webapp_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.WEBAPP_LOG_QUEUE}, "", false),

    /**
     * 移动端异常日志
     */
    PRODUCT_SCORE("product_score_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.PRODUCT_SCORE_QUEUE}, "", false),


    ;

    /**
     * mq交换机名称
     */
    private final String exchange;

    /**
     * 交换机类型
     */
    private final ExchangeType exchangeType;

    /**
     * mq队列名称
     */
    private final String[] queue;

    /**
     * 路由key
     */
    private final String routingKey;

    /**
     * 是否为延迟队列
     */
    private final boolean delayed;

}
