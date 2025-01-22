package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
public enum ChargeMode {

    /**
     * 计件
     */
    QUANTITY(1, "计件"),

    /**
     * 计重
     */
    WEIGHT(2, "计重");

    /**
     * 计费方式
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

}
