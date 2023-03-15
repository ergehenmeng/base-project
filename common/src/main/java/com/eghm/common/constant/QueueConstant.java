package com.eghm.common.constant;

/**
 * mq队列定义常亮
 * @author 二哥很猛
 * @date 2022/7/28
 */
public class QueueConstant {

    private QueueConstant() {}

    /**
     * 零售过期队列
     */
    public static final String ITEM_PAY_EXPIRE_QUEUE = "item_pay_expire_queue";

    /**
     * 门票过期队列
     */
    public static final String TICKET_PAY_EXPIRE_QUEUE = "ticket_pay_expire_queue";

    /**
     * 民宿过期队列
     */
    public static final String HOMESTAY_PAY_EXPIRE_QUEUE = "homestay_pay_expire_queue";

    /**
     * 餐饮券过期队列
     */
    public static final String VOUCHER_PAY_EXPIRE_QUEUE = "voucher_pay_expire_queue";

    /**
     * 线路过期队列
     */
    public static final String LINE_PAY_EXPIRE_QUEUE = "line_pay_expire_queue";

    /**
     * 订单发货后自动完成
     */
    public static final String ITEM_COMPLETE_QUEUE = "item_complete_queue";

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
    public static final String ITEM_ORDER_QUEUE = "item_order_queue";

    /**
     * 优惠券领取
     */
    public static final String COUPON_RECEIVE_QUEUE = "coupon_receive_queue";

    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

    /**
     * 管理后台操作日志队列
     */
    public static final String MANAGE_OP_LOG_QUEUE = "manage_op_log_queue";

    /**
     * 移动端登陆日志队列
     */
    public static final String LOGIN_LOG_QUEUE = "login_log_queue";

    /**
     * 移动端登陆日志队列
     */
    public static final String WEBAPP_LOG_QUEUE = "webapp_log_queue";
}
