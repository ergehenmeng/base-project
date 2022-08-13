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
 * @date 2019/8/9 14:59
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptEditRequest extends ActionRecord {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(message = "部门名称不能为空")
    private String title;

    @ApiModelProperty("备注信息")
    private String remark;
}
