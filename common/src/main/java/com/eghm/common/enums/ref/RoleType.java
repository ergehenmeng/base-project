package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛 2022/6/24 17:36
 */
@Getter
@AllArgsConstructor
public enum RoleType implements IEnum<String> {

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


    ;

    /**
     * 角色code
     */
    private final String value;

    /**
     * 角色名称
     */
    private final String name;

    @Override
    public String getValue() {
        return value;
    }
}
