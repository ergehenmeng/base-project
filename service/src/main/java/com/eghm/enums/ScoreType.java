package com.eghm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @since 2020/9/7
 */
@AllArgsConstructor
@Getter
public enum ScoreType {

    /**
     * 签到积分
     */
    SIGN_IN(1, "签到积分", 1),

    /**
     * 抽奖积分
     */
    LOTTERY(2, "抽奖积分", 1),
    ;

    /**
     * 类型
     */
    private final int value;

    /**
     * 说明
     */
    private final String msg;

    /**
     * 1: 收入 2: 支出
     */
    private final Integer direction;

}
