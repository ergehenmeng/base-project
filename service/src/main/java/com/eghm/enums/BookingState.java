package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2025/1/22
 */
@Getter
@AllArgsConstructor
public enum BookingState {

    /**
     * 待成团
     */
    WAITING(0, "待成团"),

    /**
     * 拼团成功
     */
    SUCCESS(1, "拼团成功"),

    /**
     * 拼团失败
     */
    FAIL(2, "拼团失败");

    private final int value;

    private final String name;
}
