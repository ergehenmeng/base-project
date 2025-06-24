package com.eghm.dto.ext;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程变量定义类
 * 1. 所有业务线程变量定义在此类中, 统一使用此类进行获取和设置
 * 2. 在特殊情况下使用线程变量进行参数传递可以在不改变业务方法的情况下进行
 * 3. 注意: 线程变量需要谨慎使用, 且及时清理防止内存泄漏
 * @author 二哥很猛
 * @since 2025/6/24
 */
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ThreadHolder {

    /**
     * 是否已发放抽奖奖励 默认发放
     */
    private static final TransmittableThreadLocal<Boolean> LOTTERY = TransmittableThreadLocal.withInitial(() -> Boolean.TRUE);

    public static void setLottery(Boolean value) {
        LOTTERY.set(value);
    }

    public static Boolean getLottery() {
        return LOTTERY.get();
    }

    public static void removeLottery() {
        LOTTERY.remove();
    }
}
