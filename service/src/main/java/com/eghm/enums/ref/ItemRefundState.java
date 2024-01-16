package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 普通商品订单退款状态
 *
 * @author 二哥很猛
 * @date 2022/9/9
 */
@AllArgsConstructor
@Getter
public enum ItemRefundState {

    /**
     * 初始状态
     */
    INIT(0, "初始状态"),

    /**
     * 已退款
     */
    REFUND(1, "已退款"),

    /**
     * 部分退款
     */
    PARTIAL_REFUND(2, "部分退款"),

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
