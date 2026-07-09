package com.eghm.enums;

import com.eghm.annotation.ExcelDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Getter
@AllArgsConstructor
public enum UserState implements ValuableEnum<Integer> {

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
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;
    public static UserState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(UserState.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
