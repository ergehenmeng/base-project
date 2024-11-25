package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Getter
@AllArgsConstructor
public enum UserState implements EnumBinder<Integer> {

    /**
     * 锁定
     */
    LOCK(0, "锁定"),

    /**
     * 正常
     */
    NORMAL(1, "正常"),

    /**
     * 注销
     */
    LOGOUT(2, "注销");

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(UserState.values()).filter(type -> value.equals(type.value)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}
