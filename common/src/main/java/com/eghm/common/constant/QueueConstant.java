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
     * 门票订单队列
     */
    public static final String TICKET_ORDER_QUEUE = "ticket_order_queue";

    /**
     * 餐饮订单队列
     */
    public static final String VOUCHER_ORDER_QUEUE = "voucher_order_queue";

    /**
     * 民宿订单队列
     */
    public static final String HOMESTAY_ORDER_QUEUE = "voucher_order_queue";

    /**
     * 商品订单队列
     */
    public static final String PRODUCT_ORDER_QUEUE = "product_order_queue";

    /**
     * 优惠券领取
     */
    public static final String COUPON_RECEIVE_QUEUE = "coupon_receive_queue";

    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

    /**
     * 管理后台日志队列
     */
    public static final String MANAGE_LOG_QUEUE = "manage_log_queue";

    /**
     * 移动端登陆日志队列
     */
    public static final String LOGIN_LOG_QUEUE = "login_log_queue";

}
