package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单关闭类型
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Getter
@AllArgsConstructor
public enum CloseType {

    /**
     * 过期自动取消
     */
    EXPIRE(1, "过期自动取消"),

    /**
     * 用户取消
     */
    CANCEL(2, "用户取消"),

    /**
     * 退款成功
     */
    REFUND(3, "退款成功"),

    ;
    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
