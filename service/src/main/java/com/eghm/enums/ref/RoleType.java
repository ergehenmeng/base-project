package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛 2022/6/24 17:36
 */
@Getter
@AllArgsConstructor
public enum RoleType {

    /**
     * 超级管理员
     */
    ADMINISTRATOR("administrator", "超级管理员"),

    /**
     * 系统通用角色
     */
    COMMON("common", "普通管理员"),

    /**
     * 景区商户角色
     */
    SCENIC("scenic", "景区"),

    /**
     * 民宿商户角色
     */
    HOMESTAY("homestay", "民宿"),

    /**
     * 线路商户角色
     */
    RESTAURANT("restaurant", "餐饮"),

    /**
     * 零售商户角色
     */
    ITEM("item", "零售"),

    /**
     * 线路商户角色
     */
    LINE("line", "线路"),

    /**
     * 线路商户角色
     */
    VENUE("venue", "线路"),

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


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
