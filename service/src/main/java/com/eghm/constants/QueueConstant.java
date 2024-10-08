package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * mq队列定义常亮
 *
 * @author 二哥很猛
 * @since 2022/7/28
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
     * 场馆预约过期队列
     */
    public static final String VENUE_PAY_EXPIRE_QUEUE = "venue_pay_expire_queue";

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
     * 订单完成 实时队列
     */
    public static final String ORDER_COMPLETE_QUEUE = "order_complete_queue";

    /**
     * 订单收货后,完成后自动评价
     */
    public static final String ITEM_COMPLETE_DELAY_QUEUE = "item_complete_delay_queue";

    /**
     * 门票核销后自动评价
     */
    public static final String TICKET_COMPLETE_DELAY_QUEUE = "ticket_complete_delay_queue";

    /**
     * 线路核销后自动评价
     */
    public static final String LINE_COMPLETE_DELAY_QUEUE = "line_complete_delay_queue";

    /**
     * 民宿核销后自动评价
     */
    public static final String HOMESTAY_COMPLETE_DELAY_QUEUE = "homestay_complete_delay_queue";

    /**
     * 餐饮核销后自动评价
     */
    public static final String RESTAURANT_COMPLETE_DELAY_QUEUE = "restaurant_complete_delay_queue";

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
     * 移动端浏览记录队列
     */
    public static final String MEMBER_VISIT_LOG_QUEUE = "member_visit_log_queue";

    /**
     * 拼团订单过期自动取消队列 (全部)
     */
    public static final String GROUP_ORDER_EXPIRE_QUEUE = "group_order_expire_queue";

    /**
     * 拼团订单过期自动取消队列 (单个)
     */
    public static final String GROUP_ORDER_EXPIRE_SINGLE_QUEUE = "group_order_expire_single_queue";

    /**
     * 商品分数队列
     */
    public static final String PRODUCT_SCORE_QUEUE = "product_score_queue";

    /**
     * 零售发货后,14日自动确认收货
     */
    public static final String ITEM_SIPPING_QUEUE = "item_sipping_queue";

    /**
     * 优惠券自动过期
     */
    public static final String COUPON_EXPIRE_QUEUE = "coupon_expire_queue";

    /**
     * 零售退款自动确认队列
     */
    public static final String ITEM_REFUND_CONFIRM_QUEUE = "item_refund_confirm_queue";

    /**
     * 订单完成 延迟分账
     */
    public static final String ORDER_COMPLETE_ROUTING_QUEUE = "order_complete_routing_queue";

    /**
     * 订单支付完成 排行榜队列
     */
    public static final String ORDER_PAY_RANKING_QUEUE = "order_pay_ranking_queue";

    /**
     * 订单支付完成 通知发货
     */
    public static final String ORDER_PAY_NOTICE_QUEUE = "order_pay_notice_queue";

    /**
     * 订单退款提交后通知审核
     */
    public static final String ORDER_AUDIT_NOTICE_QUEUE = "order_audit_notice_queue";
}
