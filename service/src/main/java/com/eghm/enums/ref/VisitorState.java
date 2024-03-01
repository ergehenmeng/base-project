package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author wyb
 * @since 2023/5/24
 */
@Getter
@AllArgsConstructor
public enum VisitorState implements EnumBinder {

    /**
     * 待支付
     */
    UN_PAY(0, "待支付"),

    /**
     * 已支付 待使用
     */
    PAID(1, "已支付"),

    /**
     * 已使用
     */
    USED(2, "已使用"),

    /**
     * 已退款(提交退款申请,尚未审核)
     */
    REFUNDING(3, "退款中"),

    /**
     * 已退款(钱已到账)
     */
    REFUND(4, "已退款"),

    ;

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static VisitorState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(VisitorState.values()).filter(type -> value.equals(type.value)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
