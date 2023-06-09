package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门信息表
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("sys_dept")
public class SysDept extends BaseEntity {

    @ApiModelProperty("父级编号")
    private String parentCode;

    @ApiModelProperty("部门编号")
    private String code;

    @ApiModelProperty("部门名称")
    private String title;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("操作人姓名")
    private String userName;

    @ApiModelProperty("操作人id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

}