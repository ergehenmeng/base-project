package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 订单支付状态
 *
 * @author 二哥很猛
 * @since 2022/7/28
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
     * 待自提
     */
    WAIT_TAKE(3, "待自提"),

    /**
     * 待发货(需要发货的商品)
     */
    WAIT_DELIVERY(4, "待发货"),

    /**
     * 部分发货(已发货了一部分)
     */
    PARTIAL_DELIVERY(5, "部分发货"),

    /**
     * 待收货(需要发货的商品)
     */
    WAIT_RECEIVE(6, "待收货"),

    /**
     * 退款中
     */
    REFUND(7, "退款中"),

    /**
     * 订单完成(已收货或已核销或已入住) 注意: 针对核销类商品直接由待使用变成已完成
     */
    COMPLETE(8, "订单完成"),

    /**
     * 已关闭
     */
    CLOSE(9, "已关闭"),

    /**
     * 支付异常
     */
    PAY_ERROR(10, "支付异常"),

    /**
     * 退款异常
     */
    REFUND_ERROR(11, "退款异常");

    /**
     * 状态
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(OrderState.values()).filter(state -> state.value == value).findFirst().orElse(null);
    }

}
