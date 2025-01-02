package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SysDept extends BaseEntity {

    @Schema(description = "父级编号")
    private String parentCode;

    @Schema(description = "部门编号")
    private String code;

    @Schema(description = "部门名称")
    private String title;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "操作人姓名")
    private String userName;

    @Schema(description = "操作人id")
    private Long userId;

}