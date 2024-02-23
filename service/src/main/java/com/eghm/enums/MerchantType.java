package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum MerchantType {

    /**
     * 景区
     */
    SCENIC(1, "景区"),

    /**
     * 民宿
     */
    HOMESTAY(2, "民宿"),

    /**
     * 餐饮
     */
    RESTAURANT(4, "餐饮"),

    /**
     * 零售
     */
    ITEM(8, "零售"),

    /**
     * 线路
     */
    LINE(16, "线路");

    /**
     * 商户类型
     */
    private final int value;

    /**
     * 商户名称
     */
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
