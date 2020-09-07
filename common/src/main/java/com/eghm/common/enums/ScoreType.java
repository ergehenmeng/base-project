package com.eghm.common.enums;

/**
 * @author 殿小二
 * @date 2020/9/7
 */
public enum ScoreType {
    /**
     * 签到积分
     */
    SIGN_IN((byte) 1, "签到积分", "+");

    /**
     * 类型
     */
    private byte value;

    /**
     * 说明
     */
    private String msg;

    /**
     * 符号 收入:+ 支出-
     */
    private String symbol;

    ScoreType(byte value, String msg, String symbol) {
        this.value = value;
        this.msg = msg;
        this.symbol = symbol;
    }

    public byte getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public String getSymbol() {
        return symbol;
    }
}
