package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 审核状态
 * @author 二哥很猛
 * @date 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum AuditState {

    /**
     * 默认
     */
    APPLY(0, "待审核"),

    /**
     * 审核通过
     */
    PASS(1, "审核通过"),

    /**
     * 审核拒绝
     */
    REFUSE(2, "审核拒绝"),

    /**
     * 取消审核(用户主动取消退款)
     */
    CANCEL(3, "取消审核"),
    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator
    public static AuditState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AuditState.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.VERIFY_STATE_ERROR));
    }
}
