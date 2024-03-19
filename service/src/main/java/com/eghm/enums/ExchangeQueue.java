package com.eghm.enums;

import com.eghm.constant.QueueConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用于定义RabbitMQ的枚举, 注意:routingKey不支持多配置
 *
 * @author 二哥很猛
 * @since 2022/6/12 18:47
 */
@Getter
@AllArgsConstructor
public enum ExchangeQueue {

    /**
     * 零售订单未支付时取消队列 延迟队列
     */
    ITEM_PAY_EXPIRE("item_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    TICKET_PAY_EXPIRE("ticket_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 场馆订单未支付时取消队列 延迟队列
     */
    VENUE_PAY_EXPIRE("venue_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.VENUE_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    HOMESTAY_PAY_EXPIRE("homestay_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.HOMESTAY_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 餐饮券订单未支付时取消队列 延迟队列
     */
    RESTAURANT_PAY_EXPIRE("restaurant_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.RESTAURANT_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 线路订单未支付时取消队列 延迟队列
     */
    LINE_PAY_EXPIRE("line_pay_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LINE_PAY_EXPIRE_QUEUE}, "", true),

    /**
     * 门票订单队列
     */
    TICKET_ORDER("ticket_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_ORDER_QUEUE}, "", false),

    /**
     * 餐饮订单队列
     */
    VOUCHER_ORDER("voucher_order_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.VOUCHER_ORDER_QUEUE}, "", false),

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
     * 商品评分统计包含(店铺评分)
     */
    PRODUCT_SCORE("product_score_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.PRODUCT_SCORE_QUEUE}, "", false),

    /**
     * 商品订单发货后 自动确认收货
     */
    ITEM_SIPPING("item_sipping_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_SIPPING_QUEUE}, "", true),

    /**
     * 商品确认收货
     */
    ITEM_COMPLETE_DELAY("item_complete_delay_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_COMPLETE_DELAY_QUEUE}, "", true),

    /**
     * 门票核销完成
     */
    TICKET_COMPLETE_DELAY("ticket_complete_delay_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.TICKET_COMPLETE_DELAY_QUEUE}, "", true),

    /**
     * 线路核销完成
     */
    LINE_COMPLETE_DELAY("line_complete_delay_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LINE_COMPLETE_DELAY_QUEUE}, "", true),

    /**
     * 餐饮核销完成
     */
    RESTAURANT_COMPLETE_DELAY("restaurant_complete_delay_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.RESTAURANT_COMPLETE_DELAY_QUEUE}, "", true),

    /**
     * 民宿核销完成
     */
    HOMESTAY_COMPLETE_DELAY("homestay_complete_delay_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.HOMESTAY_COMPLETE_DELAY_QUEUE}, "", true),

    /**
     * 订单完成
     */
    ORDER_COMPLETE("order_complete_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ORDER_COMPLETE_QUEUE}, "", false),

    /**
     * 移动端会员浏览记录
     */
    MEMBER_VISIT_LOG("member_visit_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.MEMBER_VISIT_LOG_QUEUE}, "", false),

    /**
     * 拼团订单过期取消(取消拼团下所有没有拼团成功的订单)
     */
    GROUP_ORDER_EXPIRE("group_order_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.GROUP_ORDER_EXPIRE_QUEUE}, "", true),

    /**
     * 拼团订单过期取消(取消某个拼团)
     */
    GROUP_ORDER_EXPIRE_SINGLE("group_order_expire_single_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.GROUP_ORDER_EXPIRE_SINGLE_QUEUE}, "", true),

    /**
     * 优惠券自动过期
     */
    COUPON_EXPIRE("coupon_expire_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.COUPON_EXPIRE_QUEUE}, "", true),

    /**
     * 零售退款自动确认
     */
    ITEM_REFUND_CONFIRM("item_refund_confirm_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ITEM_REFUND_CONFIRM_QUEUE}, "", true),

    /**
     * 订单完成
     */
    ORDER_COMPLETE_ROUTING("order_complete_routing_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.ORDER_COMPLETE_ROUTING_QUEUE}, "", true),
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
