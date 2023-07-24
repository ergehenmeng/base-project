package com.eghm.enums;

import com.eghm.constant.QueueConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Getter
@AllArgsConstructor
public enum RoutingKey {

    /**
     * 零售订单未支付时取消队列 延迟队列
     */
    ITEM_PAY_EXPIRE_KEY(QueueConstant.ITEM_PAY_EXPIRE_QUEUE, ""),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    TICKET_PAY_EXPIRE_KEY(QueueConstant.TICKET_PAY_EXPIRE_QUEUE, ""),

    /**
     * 门票订单未支付时取消队列 延迟队列
     */
    HOMESTAY_PAY_EXPIRE_KEY(QueueConstant.HOMESTAY_PAY_EXPIRE_QUEUE, ""),

    /**
     * 餐饮券订单未支付时取消队列 延迟队列
     */
    RESTAURANT_PAY_EXPIRE_KEY(QueueConstant.RESTAURANT_PAY_EXPIRE_QUEUE, ""),

    /**
     * 线路订单未支付时取消队列 延迟队列
     */
    LINE_PAY_EXPIRE_KEY(QueueConstant.LINE_PAY_EXPIRE_QUEUE, ""),

    /**
     * 商品订单发货后自动完成 延迟队列
     */
    ITEM_COMPLETE_KEY(QueueConstant.ITEM_COMPLETE_QUEUE, ""),

    /**
     * 门票自动完成 延迟队列
     */
    TICKET_COMPLETE_KEY(QueueConstant.TICKET_COMPLETE_QUEUE, ""),

    /**
     * 线路自动完成 延迟队列
     */
    LINE_COMPLETE_KEY(QueueConstant.LINE_COMPLETE_QUEUE, ""),

    /**
     * 餐饮自动完成 延迟队列
     */
    RESTAURANT_COMPLETE_KEY(QueueConstant.RESTAURANT_COMPLETE_QUEUE, ""),

    /**
     * 民宿自动完成 延迟队列
     */
    HOMESTAY_COMPLETE_KEY(QueueConstant.HOMESTAY_COMPLETE_QUEUE, ""),

    /**
     * 门票订单队列
     */
    TICKET_ORDER_KEY(QueueConstant.TICKET_ORDER_QUEUE, ""),

    /**
     * 餐饮订单队列
     */
    RESTAURANT_ORDER_KEY(QueueConstant.RESTAURANT_ORDER_QUEUE, ""),

    /**
     * 民宿订单队列
     */
    HOMESTAY_ORDER_KEY(QueueConstant.HOMESTAY_ORDER_QUEUE, ""),

    /**
     * 商品订单队列
     */
    ITEM_ORDER_KEY(QueueConstant.ITEM_ORDER_QUEUE, ""),

    /**
     * 线路订单队列
     */
    LINE_ORDER_KEY(QueueConstant.LINE_ORDER_QUEUE, ""),

    /**
     * 优惠券领取
     */
    COUPON_RECEIVE_KEY(QueueConstant.COUPON_RECEIVE_QUEUE, ""),

    /**
     * 管理后台操作日志队列
     */
    MANAGE_OP_LOG_KEY(QueueConstant.MANAGE_OP_LOG_QUEUE, ""),

    /**
     * 移动端用户登录日志队列
     */
    LOGIN_LOG_KEY(QueueConstant.LOGIN_LOG_QUEUE, ""),

    /**
     * 移动端异常日志
     */
    WEBAPP_LOG_KEY(QueueConstant.WEBAPP_LOG_QUEUE, ""),

    /**
     * 测试
     */
    TEST_KEY("test_queue", "");

    /**
     * mq队列名称
     */
    private final String queue;

    /**
     * 路由key
     */
    private final String key;

}