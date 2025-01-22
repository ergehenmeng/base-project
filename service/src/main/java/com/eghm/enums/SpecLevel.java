package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/2/29
 */
@Getter
@AllArgsConstructor
public enum SpecLevel {

    /**
     * 一级规格
     */
    ONE(1, "一级规格"),

    /**
     * 二级规格
     */
    TWO(2, "二级规格");

    @EnumValue
    @JsonValue
    private final int value;

    @ExcelDesc
    private final String name;
}