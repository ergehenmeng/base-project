package com.eghm.platform.iam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eghm
 */
@Data
@NoArgsConstructor
@TableName("sys_dept_data")
public class SysDeptData {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "部门id")
    private String deptCode;

    public SysDeptData(Long userId, String deptCode) {
        this.userId = userId;
        this.deptCode = deptCode;
    }
}