package com.eghm.enums;

import com.eghm.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Getter
@AllArgsConstructor
public enum UserType {

    /**
     * 系统管理员
     */
    ADMINISTRATOR(0, "系统管理员"),

    /**
     * 系统用户
     */
    SYS_USER(1, "系统用户");
    private final int value;

    private final String name;
    public static UserType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(UserType.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.USER_TYPE_NULL));
    }

}
