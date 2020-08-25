package com.eghm.common.enums;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
public enum AuditState {

    /**
     * 待审核
     */
    APPLY((byte) 0, "待审核"),

    /**
     * 审核通过
     */
    PASS((byte) 1, "审核通过") ,

    /**
     * 审核拒绝
     */
    REFUSE((byte) 2, "审核拒绝"),
    ;

    private byte value;

    private String msg;

    AuditState(byte value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public byte getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
