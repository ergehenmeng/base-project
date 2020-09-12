package com.eghm.common.enums;

/**
 * @author 殿小二
 * @date 2020/9/12
 */
public enum NoticeType {

    /**
     * 通用类通知
     */
    COMMON("COMMON", "通用类通知", false, "userNotice");

    /**
     * 分类
     */
    private String value;

    /**
     * 说明
     */
    private String msg;

    /**
     * 是否发送消息通知(极光,友盟等通知)
     */
    private boolean pushNotice;

    /**
     * 通知跳转页面
     */
    private String viewTag;

    NoticeType(String value, String msg, boolean pushNotice, String viewTag) {
        this.value = value;
        this.msg = msg;
        this.pushNotice = pushNotice;
        this.viewTag = viewTag;
    }

    public String getViewTag() {
        return viewTag;
    }

    public String getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isPushNotice() {
        return pushNotice;
    }
}
