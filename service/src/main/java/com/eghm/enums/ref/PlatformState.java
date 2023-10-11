package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台上下架状态
 * @author 二哥很猛
 * @date 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum PlatformState implements EnumBinder {

    /**
     * 默认
     */
    DEFAULT(0, "初始"),

    /**
     * 未上架
     */
    UN_SHELVE(1, "未上架"),

    /**
     * 已上架
     */
    SHELVE(2, "已上架"),
    ;

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

}
