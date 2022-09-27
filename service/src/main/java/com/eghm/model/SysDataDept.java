package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("sys_data_dept")
public class SysDataDept {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long operatorId;

    @ApiModelProperty("部门id")
    private String deptCode;

    public SysDataDept(Long operatorId, String deptCode) {
        this.operatorId = operatorId;
        this.deptCode = deptCode;
    }
}