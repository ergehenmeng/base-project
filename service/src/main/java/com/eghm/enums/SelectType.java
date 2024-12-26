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
public enum SelectType implements EnumBinder<String> {

    /**
     * 按天查询
     */
    DAY("day", "天"),

    /**
     * 按月查询
     */
    MONTH("month", "月"),
    ;

    @JsonValue
    @EnumValue
    private final String value;

    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value.equalsIgnoreCase(value.split(":")[0]);
    }
}
