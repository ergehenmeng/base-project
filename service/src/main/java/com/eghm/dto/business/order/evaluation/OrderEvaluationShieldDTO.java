package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationShieldDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("屏蔽原因")
    @NotNull(message = "请填写屏蔽原因")
    @Size(max = 100, message = "屏蔽原因最大100字符")
    private String remark;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;
}
