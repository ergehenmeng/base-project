package com.eghm.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2024/9/13
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LockKey {

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
}
