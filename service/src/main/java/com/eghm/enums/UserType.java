package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Getter
@AllArgsConstructor
public enum UserType implements EnumBinder<Integer> {

    /**
     * 系统管理员
     */
    ADMINISTRATOR(0, "系统管理员"),

    /**
     * 系统用户
     */
    SYS_USER(1, "系统用户");

    @JsonValue
    @EnumValue
    private final Integer value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserType of(@JsonProperty("value") Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(UserType.values()).filter(auditState -> auditState.value == value.intValue())
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.USER_TYPE_NULL));
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
