package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationAuditDTO {

    @ApiModelProperty(value = "id", hidden = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("审核状态 1:通过 2:拒绝")
    @OptionInt(value = {1, 2}, message = "审核状态非法")
    private Integer state;

    @ApiModelProperty("审核原因")
    private String auditRemark;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;
}
