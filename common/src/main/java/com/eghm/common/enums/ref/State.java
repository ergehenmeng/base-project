package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum State {
    
    /**
     * 初始化
     */
    INIT(-1, "初始化"),
    
    /**
     * 待上架
     */
    UN_SHELVE(0, "待上架"),

    /**
     * 已上架
     */
    SHELVE(1, "已上架"),
    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final int value;

    /**
     * 名称
     */
    private final String name;

}
