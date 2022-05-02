package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class SysDataDept extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long operatorId;

    @ApiModelProperty("部门id")
    private String deptCode;

    public SysDataDept(Long operatorId, String deptCode) {
        this.operatorId = operatorId;
        this.deptCode = deptCode;
    }
}