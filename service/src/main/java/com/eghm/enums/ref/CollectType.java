package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Getter
@AllArgsConstructor
public enum CollectType implements EnumBinder {

    /**
     * 景区
     */
    SCENIC(1, "景区"),

    /**
     * 民宿
     */
    HOMESTAY(2, "民宿"),

    /**
     * 零售门店
     */
    ITEM_STORE(3, "零售门店"),

    /**
     * 零售商品
     */
    ITEM(4, "零售商品"),

    /**
     * 线路商品
     */
    LINE(5, "线路商品"),

    /**
     * 餐饮门店
     */
    VOUCHER_STORE(6, "餐饮门店"),

    /**
     * 资讯
     */
    NEWS(7, "资讯"),

    /**
     * travel_agency
     */
    TRAVEL_AGENCY(8, "旅行社")
    ;

    /**
     * 状态
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
