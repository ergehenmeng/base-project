package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 审核状态
 *
 * @author 二哥很猛
 * @since 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum AuditState implements EnumBinder<Integer> {

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
    private final Integer value;

    /**
     * 名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AuditState of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(AuditState.values()).filter(auditState -> auditState.value == value.intValue())
                .findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}
