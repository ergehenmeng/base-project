package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2025/1/22
 */
@AllArgsConstructor
@Getter
public enum UseScope {

    /**
     * 店铺通用
     */
    STORE(1, "店铺通用"),

    /**
     * 指定商品
     */
    PRODUCT(2, "指定商品");

    private final int value;

    private final String name;
}
