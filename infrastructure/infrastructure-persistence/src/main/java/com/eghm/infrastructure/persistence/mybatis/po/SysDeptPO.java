package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门信息表
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_dept")
@EqualsAndHashCode(callSuper = true)
public class SysDeptPO extends BaseEntityPO {

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

}


