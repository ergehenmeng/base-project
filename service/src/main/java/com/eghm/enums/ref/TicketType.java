package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 票种
 *
 * @author 二哥很猛
 * @since 2024/7/2
 */
@Getter
@AllArgsConstructor
public enum TicketType implements EnumBinder {

    /**
     * 成人票
     */
    ADULT(1, "成人票"),

    /**
     * 老人票
     */
    OLD(2, "老人票"),

    /**
     * 儿童票
     */
    CHILD(3, "儿童票"),
    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
