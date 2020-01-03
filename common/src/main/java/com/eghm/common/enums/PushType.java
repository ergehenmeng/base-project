package com.eghm.common.enums;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:12
 */
public enum PushType {

    /**
     * 站内性通知
     */
    INNER_EMAIL("inner_email");

    private String nid;

    PushType(String nid) {
        this.nid = nid;
    }

    public String getNid() {
        return nid;
    }
}
