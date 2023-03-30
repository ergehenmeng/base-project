package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
@Getter
@AllArgsConstructor
public enum RefundType {

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

    @EnumValue
    @JsonValue
    private final int value;

    private final String name;

    @JsonCreator
    public static RefundType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RefundType.values()).filter(type -> value == type.value).findFirst().orElseThrow(() -> new BusinessException(ErrorCode.REFUND_TYPE_NOT_MATCH));
    }
}
