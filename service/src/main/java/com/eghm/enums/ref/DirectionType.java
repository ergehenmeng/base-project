package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2022/9/4
 */
@AllArgsConstructor
@Getter
public enum DirectionType implements EnumBinder {

    /**
     * 无须发货
     */
    INCOME(1, "收入"),

    /**
     * 快递包邮
     */
    DISBURSE(2, "支出"),

    ;

    @JsonValue
    @EnumValue
    private final int value;

    @ExcelDesc
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
