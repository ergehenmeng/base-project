package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eghm
 */
@Data
@NoArgsConstructor
@TableName("sys_data_dept")
public class SysDataDept {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("部门id")
    private String deptCode;

    public SysDataDept(Long userId, String deptCode) {
        this.userId = userId;
        this.deptCode = deptCode;
    }
}