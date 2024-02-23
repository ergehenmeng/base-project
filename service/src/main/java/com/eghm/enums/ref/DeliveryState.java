package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Getter
@AllArgsConstructor
public enum DeliveryState implements EnumBinder {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 待发货
     */
    WAIT_DELIVERY(1, "待发货"),

    /**
     * 待收货
     */
    WAIT_TAKE(2, "待收货"),

    /**
     * 已签收/已收货
     */
    CONFIRM_TASK(3, "已签收");

    @JsonValue
    @EnumValue
    private final int value;

    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
