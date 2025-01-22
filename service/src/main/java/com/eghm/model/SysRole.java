package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色类型")
    private RoleType roleType;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "商户id")
    private Long merchantId;
}