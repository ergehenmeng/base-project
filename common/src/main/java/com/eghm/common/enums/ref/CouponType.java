package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠券类型
 * @author 二哥很猛
 * @date 2022/7/14
 */
@Getter
@AllArgsConstructor
public enum CouponType implements IEnum<Integer>  {

    /**
     * 抵扣券
     */
    DEDUCTION(1, "抵扣券"),

    /**
     * 折扣券
     */
    DISCOUNT(2, "折扣券");

    /**
     * 方式
     */
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }

    @JsonCreator
    public static CouponType valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CouponType.values()).filter(couponMode -> couponMode.value == value).findFirst().orElse(DEDUCTION);
    }
}
