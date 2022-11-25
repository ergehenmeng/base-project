package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum State implements IEnum<Integer> {

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
    private final int value;

    /**
     * 名称
     */
    private final String name;

    @Override
    @JsonValue
    public Integer getValue() {
        return value;
    }
}
