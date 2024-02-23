package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠券类型
 *
 * @author 二哥很猛
 * @since 2022/7/14
 */
@Getter
@AllArgsConstructor
public enum CouponType implements EnumBinder {

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
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CouponType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CouponType.values()).filter(couponMode -> couponMode.value == value).findFirst().orElse(DEDUCTION);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
