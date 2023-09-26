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
     * 普通管理员
     */
    COMMON("common", "普通管理员"),

    /**
     * 景区商户
     */
    SCENIC("scenic", "景区商户"),

    /**
     * 民宿商户
     */
    HOMESTAY("homestay", "民宿商户"),

    /**
     * 餐饮商户
     */
    RESTAURANT("restaurant", "餐饮商户"),

    /**
     * 特产商户
     */
    SPECIALTY("specialty", "特产商户"),

    /**
     * 线路商户
     */
    LINE("line", "线路商户"),

    /**
     * 商家普通用户
     */
    MERCHANT("merchant", "商家普通用户"),
    ;

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

}
