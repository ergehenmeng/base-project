package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/8/12
 */

@Getter
@AllArgsConstructor
public enum SelectType implements ValueEnumBinder {

    WEEK("week", "周"),

    MONTH("month", "月"),

    YEAR("year", "年"),

    CUSTOM("custom", "自定义");

    @JsonValue
    @EnumValue
    private final String value;

    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
