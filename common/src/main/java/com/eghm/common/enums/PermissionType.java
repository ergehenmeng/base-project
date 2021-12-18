package com.eghm.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@AllArgsConstructor
@Getter
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

    private final byte value;

    private final String msg;

}
