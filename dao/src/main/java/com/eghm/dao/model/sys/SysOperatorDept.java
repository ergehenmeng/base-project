package com.eghm.dao.model.sys;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysOperatorDept implements Serializable {

    /**
     * <br>
     * 表 : sys_operator_dept<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户id<br>
     * 表 : sys_operator_dept<br>
     * 对应字段 : operator_id<br>
     */
    private Integer operatorId;

    /**
     * 部门id<br>
     * 表 : sys_operator_dept<br>
     * 对应字段 : dept_code<br>
     */
    private String deptCode;

    private static final long serialVersionUID = 1L;

    public SysOperatorDept() {
    }

    public SysOperatorDept(Integer operatorId, String deptCode) {
        this.operatorId = operatorId;
        this.deptCode = deptCode;
    }
}