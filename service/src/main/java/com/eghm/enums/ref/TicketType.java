package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.annotation.ExcelDesc;
import com.eghm.enums.EnumBinder;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 票种
 *
 * @author 二哥很猛
 * @since 2024/7/2
 */
@Getter
@AllArgsConstructor
public enum TicketType implements EnumBinder<Integer> {

    /**
     * 成人
     */
    ADULT(1, "成人"),

    /**
     * 老人
     */
    OLD(2, "老人"),

    /**
     * 儿童
     */
    CHILD(3, "儿童"),

    /**
     * 演出
     */
    SHOW(4, "演出"),

    /**
     * 活动
     */
    ACTIVITY(5, "活动"),

    /**
     *  研学
     */
    STUDY(6, "研学"),

    /**
     *  组合 默认实名制,且组合票内不能继续套组合票
     */
    COMBINE(7, "组合"),
    ;

    /**
     * 状态值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;

    @Override
    public String toString() {
        return value + ":" + name;
    }

    @Override
    public boolean match(String value) {
        return this.value == Integer.parseInt(value.split(":")[0]);
    }
}
