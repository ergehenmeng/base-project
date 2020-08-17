package com.eghm.model.dto.sys.operator;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/30 16:37
 */
@Data
public class OperatorEditRequest implements Serializable {
    private static final long serialVersionUID = 2695362882925858038L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户姓名
     */
    private String operatorName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户所属部门编号
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
