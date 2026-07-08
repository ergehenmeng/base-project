package com.eghm.enums;

import com.eghm.annotation.ExcelDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @since 2022/9/4
 */
@AllArgsConstructor
@Getter
public enum DirectionType {

    /**
     * 收入
     */
    INCOME(1, "收入"),

    /**
     * 支出
     */
    DISBURSE(2, "支出");
    private final int value;

    @ExcelDesc
    private final String name;
    public static DirectionType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DirectionType.values()).filter(type -> value == type.value).findFirst().orElse(null);
    }

}
