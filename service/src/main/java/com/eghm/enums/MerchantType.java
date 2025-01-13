package com.eghm.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

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
    VOUCHER(4, "餐饮"),

    /**
     * 零售
     */
    ITEM(8, "零售"),

    /**
     * 线路
     */
    LINE(16, "线路"),

    /**
     * 场馆
     */
    VENUE(32, "场馆");

    /**
     * 商户类型
     */
    private final int value;

    /**
     * 商户名称
     */
    private final String name;

    public static List<Integer> parse(Integer type) {
        List<Integer> list = Lists.newArrayList();
        if (type == null) {
            return list;
        }
        int index = 1;
        for (int i = 0; i < values().length; i++) {
            int value = type & (index << i);
            if (value != 0) {
                list.add(value);
            }
        }
        return list;
    }
}
