package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 评论对象类型
 *
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Getter
@AllArgsConstructor
public enum ObjectType implements EnumBinder<Integer> {

    /**
     * 资讯
     */
    NEWS(1, "资讯"),

    /**
     * 活动
     */
    ACTIVITY(2, "活动"),
    ;

    /**
     * 状态
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ObjectType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ObjectType.values()).filter(auditState -> auditState.value == value.intValue()).findFirst().orElse(null);
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
