package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
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
public enum UserType implements EnumBinder {

    /**
     * 系统管理员
     */
    ADMINISTRATOR(0, "系统管理员"),

    /**
     * 系统用户
     */
    SYS_USER(1, "系统用户"),

    /**
     * 商户用户
     */
    MERCHANT_ADMIN(2, "商户管理员"),

    /**
     * 商户普通用户
     */
    MERCHANT_USER(3, "商户普通用户");


    @JsonValue
    @EnumValue
    private final int value;

    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(UserType.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.USER_TYPE_NULL));
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }
}
