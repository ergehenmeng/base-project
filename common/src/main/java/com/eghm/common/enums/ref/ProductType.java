package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Getter
@AllArgsConstructor
public enum ProductType implements IEnum<String> {

    /**
     * 景区门票
     */
    TICKET("ticket", "门票", "MP"),

    /**
     * 餐饮券
     */
    VOUCHER("voucher", "餐饮券", "CY"),

    /**
     * 民宿
     */
    HOMESTAY("homestay", "民宿", "MS"),

    /**
     * 特产
     */
    SPECIALTY("specialty", "特产", "TC"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 前缀
     */
    private final String  prefix;

    @Override
    public String getValue() {
        return value;
    }
}
