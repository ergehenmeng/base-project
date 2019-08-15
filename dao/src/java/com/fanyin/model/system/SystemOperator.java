
package com.fanyin.model.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
public class SystemOperator implements Serializable {

    private static final long serialVersionUID = -4647762860063174639L;
    /**
     * 主键<br>
     * 表 : system_operator<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户姓名<br>
     * 表 : system_operator<br>
     * 对应字段 : operator_name<br>
     */
    private String operatorName;

    /**
     * 手机号码(登陆账户)<br>
     * 表 : system_operator<br>
     * 对应字段 : mobile<br>
     */
    private String mobile;

    /**
     * 用户状态:0:锁定,1:正常<br>
     * 表 : system_operator<br>
     * 对应字段 : state<br>
     */
    private Integer state;

    /**
     * 登陆密码MD5<br>
     * 表 : system_operator<br>
     * 对应字段 : pwd<br>
     */
    private String pwd;

    /**
     * 初始密码<br>
     * 表 : system_operator<br>
     * 对应字段 : init_pwd<br>
     */
    private String initPwd;


    /**
     * <br>
     * 表 : system_operator<br>
     * 对应字段 : department<br>
     */
    private String department;

    /**
     * 删除状态 0:正常,1:已删除<br>
     * 表 : system_operator<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 添加时间<br>
     * 表 : system_operator<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : system_operator<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 备注信息<br>
     * 表 : system_operator<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 用户的左侧菜单权限
     */
    private List<SystemMenu> leftMenu;

    /**
     * 用户的按钮权限
     */
    private List<SystemMenu> buttonMenu;
}