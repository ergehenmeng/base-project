package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/14
 */
@AllArgsConstructor
@Getter
public enum CouponState implements IEnum<Integer> {

    /**
     * 未使用
     */
    UNUSED(0, "未使用"),

    /**
     * 已使用
     */
    USED(1, "已使用"),

    /**
     * 已过期
     */
    EXPIRE(2, "已过期")

    ;

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
}
