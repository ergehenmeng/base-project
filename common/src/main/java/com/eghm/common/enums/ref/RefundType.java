package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
@Getter
@AllArgsConstructor
public enum RefundType implements IEnum<Integer> {

    /**
     * 不支持退款
     */
    NOT_SUPPORTED(0, "不支持退款"),

    /**
     * 直接退款
     */
    DIRECT_REFUND(1, "直接退款"),

    /**
     * 审核后退款
     */
    AUDIT_REFUND(2, "审核后退款"),
    ;

    private final int value;

    private final String name;


    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }
}
