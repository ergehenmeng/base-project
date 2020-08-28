package com.eghm.common.enums;

/**
 * 审核类型
 * @author 殿小二
 * @date 2020/8/25
 */
public enum AuditType {

    /**
     * 开户申请
     */
    OPEN_ACCOUNT("开户申请", "OA.", "");

    /**
     * 显示信息
     */
    private String msg;

    /**
     * 审批单号前缀
     */
    private String prefix;

    /**
     * 审批处理逻辑
     */
    private String handler;

    AuditType(String msg, String prefix, String handler) {
        this.msg = msg;
        this.prefix = prefix;
        this.handler = handler;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getMsg() {
        return msg;
    }

    public String getHandler() {
        return handler;
    }
}
