package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 普通商品订单退款状态
 *
 * @author 二哥很猛
 * @since 2022/9/9
 */
@AllArgsConstructor
@Getter
public enum ItemRefundState implements EnumBinder {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 已退款
     */
    REFUND(1, "已退款"),
    ;

    /**
     * 状态
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 备注
     */
    private final String name;


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
