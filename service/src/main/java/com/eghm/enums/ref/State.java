package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum State implements EnumBinder<Integer> {

    /**
     * 待上架
     */
    UN_SHELVE(0, "待上架"),

    /**
     * 已上架
     */
    SHELVE(1, "已上架"),

    /**
     * 平台下架
     */
    FORCE_UN_SHELVE(2, "平台下架"),
    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
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
