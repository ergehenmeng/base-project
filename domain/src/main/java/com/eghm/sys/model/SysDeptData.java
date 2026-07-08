package com.eghm.sys.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author eghm
 */
@Data
@NoArgsConstructor
public class SysDeptData {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 部门id */
    private String deptCode;

    public SysDeptData(Long userId, String deptCode) {
        this.userId = userId;
        this.deptCode = deptCode;
    }
}
