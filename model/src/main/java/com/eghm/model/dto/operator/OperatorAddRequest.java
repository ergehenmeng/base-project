package com.eghm.model.dto.operator;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理人员 添加
 * @author 二哥很猛
 * @date 2018/11/30 15:43
 */
@Data
public class OperatorAddRequest implements Serializable {

    private static final long serialVersionUID = 6228244128468433700L;

    /**
     * 名称
     */
    private String operatorName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 所属部门编号
     */
    private String deptCode;

    /**
     * 角色列表
     */
    private String roleIds;

    /**
     * 数据权限类型
     */
    private Byte permissionType;

    /**
     * 自定义数据权限时的部门id
     */
    private String deptIds;

    /**
     * 备注信息
     */
    private String remark;
}
