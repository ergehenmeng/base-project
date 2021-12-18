
package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
public class SysOperator extends BaseEntity {

    /**
     * 正常
     */
    public static final byte STATE_1 = 1;

    /**
     * 锁定
     */
    public static final byte STATE_0 = 0;

    /**
     * 用户姓名<br>
     * 表 : sys_operator<br>
     * 对应字段 : operator_name<br>
     */
    private String operatorName;

    /**
     * 手机号码(登陆账户)<br>
     * 表 : sys_operator<br>
     * 对应字段 : mobile<br>
     */
    private String mobile;

    /**
     * 用户状态:0:锁定,1:正常<br>
     * 表 : sys_operator<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 登陆密码MD5<br>
     * 表 : sys_operator<br>
     * 对应字段 : pwd<br>
     */
    private String pwd;

    /**
     * 初始密码<br>
     * 表 : sys_operator<br>
     * 对应字段 : init_pwd<br>
     */
    private String initPwd;

    /**
     * <br>
     * 表 : sys_operator<br>
     * 对应字段 : dept_code<br>
     */
    private String deptCode;

    /**
     * <br>
     * 表 : sys_operator<br>
     * 对应字段 : permission_type<br>
     */
    private Byte permissionType;

    /**
     * 删除状态 0:正常,1:已删除<br>
     * 表 : sys_operator<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 备注信息<br>
     * 表 : sys_operator<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 用户的左侧菜单权限
     */
    private List<SysMenu> leftMenu;

    /**
     * 用户的按钮权限
     */
    private List<SysMenu> buttonMenu;

    /**
     * 用户的部门数据权限
     */
    private List<String> deptList;
}