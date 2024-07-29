package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

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
    private String remark;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;
}
