package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2025/1/22
 */
@Getter
@AllArgsConstructor
public enum RedeemGrantState {

    /**
     * 待使用
     */
    WAIT_USE(0, "待使用"),

    /**
     * 已使用
     */
    USED(1, "已使用"),

    /**
     * 已过期
     */
    EXPIRE(2, "已过期");

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RedeemGrantState of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RedeemGrantState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }
}
