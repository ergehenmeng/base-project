package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态
 * @author 二哥很猛
 * @date 2022/7/2
 */
@Getter
@AllArgsConstructor
public enum AuditState implements IEnum<Integer> {

    /**
     * 默认
     */
    APPLY(0, "待审核"),

    /**
     * 审核通过
     */
    PASS(1, "审核通过"),

    /**
     * 审核拒绝
     */
    REFUSE(2, "审核拒绝"),


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
