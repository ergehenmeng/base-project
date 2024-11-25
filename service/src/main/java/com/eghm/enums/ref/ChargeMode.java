package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 计费方式
 *
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Getter
@AllArgsConstructor
public enum ChargeMode implements EnumBinder<Integer> {


    /**
     * 计件
     */
    QUANTITY(1, "计件"),

    /**
     * 计重
     */
    WEIGHT(2, "计重"),

    ;

    /**
     * 计费方式
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 名称
     */
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
