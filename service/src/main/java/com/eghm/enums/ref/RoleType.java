package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛 2022/6/24 17:36
 */
@Getter
@AllArgsConstructor
public enum RoleType implements EnumBinder<String> {

    /**
     * 系统通用角色
     */
    COMMON("common", "系统通用角色"),

    /**
     * 景区商户角色
     */
    SCENIC("scenic", "景区"),

    /**
     * 民宿商户角色
     */
    HOMESTAY("homestay", "民宿"),

    /**
     * 餐饮商户角色
     */
    VOUCHER("voucher", "餐饮"),

    /**
     * 零售商户角色
     */
    ITEM("item", "零售"),

    /**
     * 线路商户角色
     */
    LINE("line", "线路"),

    /**
     * 场馆商户角色
     */
    VENUE("venue", "场馆"),

    /**
     * 商户普通角色
     */
    MERCHANT("merchant", "商户普通角色");

    /**
     * 角色code
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 角色名称
     */
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public static RoleType of(@JsonProperty("value") String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(RoleType.values()).filter(type -> value.equals(type.value)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value.equals(value.split(":")[0]);
    }
}
