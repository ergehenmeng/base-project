package com.eghm.dto.sys.dept;

import com.eghm.dto.ext.ActionRecord;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "父节点code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "父节点code不能为空")
    private String parentCode;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "部门名称不能为空")
    private String title;

    @Schema(description = "备注信息")
    private String remark;

}
