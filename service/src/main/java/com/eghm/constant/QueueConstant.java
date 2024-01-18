package com.eghm.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * mq队列定义常亮
 *
 * @author 二哥很猛
 * @date 2022/7/28
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueueConstant {

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
    public static final String RESTAURANT_PAY_EXPIRE_QUEUE = "restaurant_pay_expire_queue";

    /**
     * 线路过期队列
     */
    public static final String LINE_PAY_EXPIRE_QUEUE = "line_pay_expire_queue";

    /**
     * 订单收货后,完成后自动评价
     */
    public static final String ITEM_COMPLETE_QUEUE = "item_complete_queue";

    /**
     * 门票核销后自动评价
     */
    public static final String TICKET_COMPLETE_QUEUE = "ticket_complete_queue";

    /**
     * 线路核销后自动评价
     */
    public static final String LINE_COMPLETE_QUEUE = "line_complete_queue";

    /**
     * 民宿核销后自动评价
     */
    public static final String HOMESTAY_COMPLETE_QUEUE = "homestay_complete_queue";

    /**
     * 餐饮核销后自动评价
     */
    public static final String RESTAURANT_COMPLETE_QUEUE = "restaurant_complete_queue";

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
    public static final String HOMESTAY_ORDER_QUEUE = "homestay_order_queue";

    /**
     * 商品订单队列
     */
    public static final String ITEM_ORDER_QUEUE = "item_order_queue";

    /**
     * 线路订单队列
     */
    public static final String LINE_ORDER_QUEUE = "line_order_queue";

    /**
     * 优惠券领取
     */
    public static final String COUPON_RECEIVE_QUEUE = "coupon_receive_queue";

    /**
     * 管理后台操作日志队列
     */
    public static final String MANAGE_OP_LOG_QUEUE = "manage_op_log_queue";

    /**
     * 移动端登陆日志队列, 主要记录用户登陆设备信息
     */
    public static final String LOGIN_LOG_QUEUE = "login_log_queue";

    /**
     * 移动端操作日志队列
     */
    public static final String WEBAPP_LOG_QUEUE = "webapp_log_queue";

    /**
     * 商品分数队列
     */
    public static final String PRODUCT_SCORE_QUEUE = "product_score_queue";

    /**
     * 零售发货后,14日自动确认收货
     */
    public static final String ITEM_SIPPING_QUEUE = "item_sipping_queue";
}
