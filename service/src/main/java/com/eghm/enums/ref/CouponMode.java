package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠券领取方式
 *
 * @author 二哥很猛
 * @since 2022/7/14
 */
@Getter
@AllArgsConstructor
public enum CouponMode implements EnumBinder {

    /**
     * 页面领取
     */
    PAGE_RECEIVE(1, "页面领取"),

    /**
     * 手动方法
     */
    GRANT(2, "手动方法");

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
    public static CouponMode of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(CouponMode.values()).filter(couponMode -> couponMode.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
