package com.eghm.common.enums;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
public enum PermissionType {

    /**
     * 个人权限
     */
    SELF((byte)1, "本人数据权限"),

    /**
     * 本部门数据权限
     */
    SELF_DEPT((byte)2, "本部门数据权限"),

    /**
     * 本部门及子部门数据权限
     */
    DEPT((byte)3, "本部门及子部门数据权限"),

    /**
     * 自定义数据权限
     */
    CUSTOM((byte)4, "自定义数据权限"),

    /**
     * 所有数据权限
     */
    ALL((byte)5, "所有数据权限"),


    ;

    PermissionType(byte value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    private byte value;

    private String msg;


    public byte getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
