package com.eghm.sys.model;

import com.eghm.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门信息表
 *
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {

    /** 父级编号 */
    private String parentCode;

    /** 部门编号 */
    private String code;

    /** 部门名称 */
    private String title;

    /** 备注信息 */
    private String remark;

    /** 操作人姓名 */
    private String userName;

    /** 操作人id */
    private Long userId;

    /**
     * 设置部门编号
     *
     * @param code 部门编号
     */
    public void assignCode(String code) {
        this.code = code;
    }

    /**
     * 记录操作人
     *
     * @param userId   操作人id
     * @param userName 操作人姓名
     */
    public void recordOperator(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
