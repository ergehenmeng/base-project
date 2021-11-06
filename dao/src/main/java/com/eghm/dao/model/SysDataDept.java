package com.eghm.dao.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class SysDataDept extends BaseEntity {

    /**
     * 用户id<br>
     * 表 : sys_data_dept<br>
     * 对应字段 : operator_id<br>
     */
    private Long operatorId;

    /**
     * 部门id<br>
     * 表 : sys_data_dept<br>
     * 对应字段 : dept_code<br>
     */
    private String deptCode;

    private static final long serialVersionUID = 1L;

    public SysDataDept() {
    }

    public SysDataDept(Long operatorId, String deptCode) {
        this.operatorId = operatorId;
        this.deptCode = deptCode;
    }
}