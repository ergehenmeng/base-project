package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单支付状态
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum OrderState implements EnumBinder {

    /**
     * 初始状态
     */
    NONE(-1, "初始状态"),

    /**
     * 待支付
     */
    UN_PAY(0, "待支付"),

    /**
     * 支付处理中
     */
    PROGRESS(1, "支付中"),

    /**
     * 待使用(不需要发货的商品)
     */
    UN_USED(2, "待使用"),

    /**
     * 待发货(需要发货的商品)
     */
    WAIT_DELIVERY(3, "待发货"),

    /**
     * 待收货(需要发货的商品)
     */
    WAIT_RECEIVE(4, "待收货"),

    /**
     * 退款中
     */
    REFUND(5, "退款中"),

    /**
     * 待评价, 注意: 针对核销类商品直接由待使用到待评价
     */
    APPRAISE(6, "待评价"),

    /**
     * 订单完成
     */
    COMPLETE(7, "订单完成"),

    /**
     * 已关闭
     */
    CLOSE(8, "已关闭"),

    /**
     * 支付异常
     */
    PAY_ERROR(9, "支付异常"),

    /**
     * 退款异常
     */
    REFUND_ERROR(10, "退款异常"),

    ;
    /**
     * 状态
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(OrderState.values()).filter(state -> state.value == value).findFirst().orElse(NONE);
    }
}
