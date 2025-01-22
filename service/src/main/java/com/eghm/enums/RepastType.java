package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2025/1/22
 */
@Getter
@AllArgsConstructor
public enum RepastType {

    /**
     * 早餐
     */
    BREAKFAST(1, "早餐"),

    /**
     * 午餐
     */
    LUNCH(2, "午餐"),

    /**
     * 晚餐
     */
    SUPPER(4, "晚餐");

    private final int value;

    private final String name;
}
