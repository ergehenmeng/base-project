package com.eghm.dto.sys.dept;

import com.eghm.dto.ext.ActionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2018/12/14 14:11
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptAddRequest extends ActionRecord {

    @ApiModelProperty(value = "父节点code", required = true)
    @NotBlank(message = "父节点code不能为空")
    private String parentCode;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank(message = "部门名称不能为空")
    private String title;

    @ApiModelProperty("备注信息")
    private String remark;

}
