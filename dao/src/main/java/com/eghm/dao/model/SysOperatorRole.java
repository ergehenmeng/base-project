package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *
 * @author 二哥很猛
 */
@Data
@AllArgsConstructor
@TableName("sys_operator_role")
public class SysOperatorRole {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long operatorId;

    @ApiModelProperty("角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    public SysOperatorRole(Long operatorId, Long roleId) {
        this.operatorId = operatorId;
        this.roleId = roleId;
    }
}