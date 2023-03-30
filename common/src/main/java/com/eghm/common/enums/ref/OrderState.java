package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
public enum OrderState {

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
     * 已核销(不需要发货的商品)
     */
    VERIFY(3, "已核销"),

    /**
     * 待发货(需要发货的商品)
     */
    WAIT_DELIVERY(4, "待发货"),

    /**
     * 待收货(需要发货的商品)
     */
    WAIT_RECEIVE(5, "待收货"),

    /**
     * 退款中
     */
    REFUND(6, "退款中"),

    /**
     * 待评价
     */
    APPRAISE(7, "待评价"),

    /**
     * 订单完成
     */
    COMPLETE(8, "订单完成"),

    /**
     * 已关闭
     */
    CLOSE(9, "已关闭"),

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

    @JsonCreator
    public static OrderState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(OrderState.values()).filter(state -> state.value == value).findFirst().orElse(NONE);
    }
}
