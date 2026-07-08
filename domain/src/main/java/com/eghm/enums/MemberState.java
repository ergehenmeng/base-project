package com.eghm.enums;

import com.eghm.annotation.ExcelDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Getter
@AllArgsConstructor
public enum MemberState {

    /**
     * 正常
     */
    NORMAL(true, "正常"),

    /**
     * 冻结
     */
    FREEZE(false, "冻结");

    /**
     * 状态
     */
    private final boolean value;

    /**
     * 名称
     */
    @ExcelDesc
    private final String name;
}
