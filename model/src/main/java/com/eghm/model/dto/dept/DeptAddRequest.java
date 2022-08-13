package com.eghm.model.dto.dept;

import com.eghm.model.dto.ext.ActionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2018/12/14 14:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptAddRequest extends ActionRecord {

    private static final long serialVersionUID = -4129318805129787627L;

    @ApiModelProperty(value = "父节点code", required = true)
    @NotNull(message = "父节点code不能为空")
    private String parentCode;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(message = "部门名称不能为空")
    private String title;

    @ApiModelProperty("备注信息")
    private String remark;

}
