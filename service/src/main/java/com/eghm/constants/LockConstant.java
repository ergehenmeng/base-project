package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2024/9/13
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockConstant {

    /**
     * 互斥锁
     */
    public static final String MUTEX_LOCK = "mutex_lock:";

    /**
     * 订单锁
     */
    public static final String ORDER_LOCK = "order_lock:";

    /**
     * 抽奖
     */
    public static final String LOTTERY_LOCK = "lottery_lock:%s:%s";

    /**
     * 零售订单锁
     */
    public static final String ITEM_ORDER_LOCK = "item_order_lock:";

    /**
     * 门票订单锁
     */
    public static final String TICKET_ORDER_LOCK = "ticket_order_lock:";

    /**
     * 餐饮券订单锁
     */
    public static final String VOUCHER_ORDER_LOCK = "voucher_order_lock:";

    /**
     * 民宿订单锁
     */
    public static final String HOMESTAY_ORDER_LOCK = "homestay_order_lock:";

    /**
     * 场馆订单锁
     */
    public static final String VENUE_ORDER_LOCK = "venue_order_lock:";

    /**
     * 线路订单锁
     */
    public static final String LINE_ORDER_LOCK = "line_order_lock:";

    /**
     * 退款锁
     */
    public static final String REFUND_LOCK = "refund_lock:";
}
