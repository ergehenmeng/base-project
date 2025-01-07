package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("sys_data_dept")
public class SysDataDept {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "部门id")
    private String deptCode;

    public SysDataDept(Long userId, String deptCode) {
        this.userId = userId;
        this.deptCode = deptCode;
    }
}