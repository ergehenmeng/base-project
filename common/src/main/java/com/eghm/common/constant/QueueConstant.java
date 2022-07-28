package com.eghm.common.constant;

/**
 * mq队列定义常亮
 * @author 二哥很猛
 * @date 2022/7/28
 */
public class QueueConstant {

    private QueueConstant() {}

    /**
     * 订单过期队列
     */
    public static final String ORDER_PAY_EXPIRE_QUEUE = "order_pay_expire_queue";

    /**
     * 订单发货后自动完成
     */
    public static final String ORDER_COMPLETE_QUEUE = "order_complete_queue";

    /**
     * 创建订单队列
     */
    public static final String ORDER_CREATE_QUEUE = "order_create_queue";

    /**
     * 优惠券领取
     */
    public static final String COUPON_RECEIVE_QUEUE = "coupon_receive_queue";

    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";



}
