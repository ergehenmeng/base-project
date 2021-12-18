package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
@AllArgsConstructor
@Getter
public enum ScoreType {
    /**
     * 签到积分
     */
    SIGN_IN((byte) 1, "签到积分", "+");

    /**
     * 类型
     */
    private final byte value;

    /**
     * 说明
     */
    private final String msg;

    /**
     * 符号 收入:+ 支出-
     */
    private final String symbol;

}
