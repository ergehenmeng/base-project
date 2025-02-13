package com.eghm.dto.sys.dept;

import com.eghm.dto.ext.ActionRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 * @since 2019/8/9 14:59
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptEditRequest extends ActionRecord {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "部门名称不能为空")
    private String title;

    @Schema(description = "父节点code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "父节点code不能为空")
    private String parentCode;

    @Schema(description = "备注信息")
    private String remark;
}
