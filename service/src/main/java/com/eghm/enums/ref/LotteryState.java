package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wyb
 * @date 2023/3/28 14:50
 */
@Getter
@AllArgsConstructor
public enum LotteryState {

    /**
     * 未开始
     */
    INIT(0, "未开始"),

    /**
     * 进行中
     */
    START(1, "进行中"),

    /**
     * 已结束
     */
    END(2, "已结束"),

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


    @Override
    public String toString() {
        return value + ":" + name;
    }
}
