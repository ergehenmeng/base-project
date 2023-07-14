package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@AllArgsConstructor
@Getter
public enum DataType {

    /**
     * 个人权限
     */
    SELF(1, "本人数据权限"),

    /**
     * 本部门数据权限
     */
    SELF_DEPT(2, "本部门数据权限"),

    /**
     * 本部门及子部门数据权限
     */
    DEPT(3, "本部门及子部门数据权限"),

    /**
     * 自定义数据权限
     */
    CUSTOM(4, "自定义数据权限"),

    /**
     * 所有数据权限
     */
    ALL(5, "所有数据权限"),

    ;
    @JsonValue
    @EnumValue
    private final int value;

    private final String msg;

    @JsonCreator
    public static DataType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(DataType.values()).filter(auditState -> auditState.value == value)
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.DATA_TYPE_ERROR));
    }
}
