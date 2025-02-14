package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @EnumValue
    @JsonValue
    private final int value;

    private final String name;
}
