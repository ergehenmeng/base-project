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
public enum HomestayLevel implements EnumBinder<Integer> {

    /**
     * 无
     */
    ZERO(0, "无"),

    /**
     * 二星级
     */
    TWO(2, "二星级"),

    /**
     * 三星级
     */
    THREE(3, "三星级"),

    /**
     * 四星级
     */
    FOUR(4, "四星级"),

    /**
     * 五星级
     */
    FIVE(5, "五星级")

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