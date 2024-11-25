package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/2/29
 */
@Getter
@AllArgsConstructor
public enum ScenicLevel implements EnumBinder<Integer> {

    /**
     * 无
     */
    ZERO(0, "无"),

    /**
     * 3A
     */
    THREE(3, "3A"),

    /**
     * 4A
     */
    FOUR(4, "4A"),

    /**
     * 5A
     */
    FIVE(5, "5A")

    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    @ExcelDesc
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}