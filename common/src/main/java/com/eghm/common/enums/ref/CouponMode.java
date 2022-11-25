package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠券领取方式
 * @author 二哥很猛
 * @date 2022/7/14
 */
@Getter
@AllArgsConstructor
public enum CouponMode implements IEnum<Integer>  {

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

    public static CouponMode valueOf(int value) {
        return Arrays.stream(CouponMode.values()).filter(couponMode -> couponMode.value == value).findFirst().orElse(PAGE_RECEIVE);
    }
}
