package com.eghm.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @since 2024/3/9
 */
@Getter
@AllArgsConstructor
public enum ReportType implements EnumBinder<Integer> {

    /**
     * 淫秽色情
     */
    EROTICISM(1, "淫秽色情"),

    /**
     * 营销广告
     */
    ADVERTISING(2, "营销广告"),

    /**
     * 违法信息
     */
    ILLEGAL(3, "违法信息"),

    /**
     * 网络暴力
     */
    VIOLENCE(4, "网络暴力"),

    /**
     * 虚假谣言
     */
    RUMOR(5, "虚假谣言"),

    /**
     * 养老诈骗
     */
    SWINDLE(6, "养老诈骗"),

    /**
     * 其他
     */
    OTHER(7, "其他"),
    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    @ExcelDesc
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReportType of(Integer value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(ReportType.values()).filter(type -> value.intValue() == type.value).findFirst().orElse(null);
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
