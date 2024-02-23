package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2022/7/14
 */
@AllArgsConstructor
@Getter
public enum RedeemState implements EnumBinder {

    /**
     * 未使用
     */
    UNUSED(0, "未使用"),

    /**
     * 已使用
     */
    USED(1, "已使用"),

    /**
     * 已过期
     */
    EXPIRE(2, "已过期");

    /**
     * 方式
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
