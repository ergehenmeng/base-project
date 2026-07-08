package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eghm
 */
@Data
@TableName("sys_dept_data")
@NoArgsConstructor
public class SysDeptDataPO {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 部门id */
    private String deptCode;

    public SysDeptDataPO(Long userId, String deptCode) {
        this.userId = userId;
        this.deptCode = deptCode;
    }
}

