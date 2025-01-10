package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2022/7/14
 */
@AllArgsConstructor
@Getter
public enum RedeemState {

    /**
     * 待使用
     */
    UNUSED(0, "待使用"),

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
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RedeemState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RedeemState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
