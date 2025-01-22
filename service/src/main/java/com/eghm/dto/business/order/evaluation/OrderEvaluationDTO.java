package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class OrderEvaluationDTO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "是否匿名评论 0:非匿名 1:匿名")
    private Boolean anonymity;

    @Schema(description = "评价信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "评价信息不能为空")
    private List<EvaluationDTO> commentList;

    @Assign
    @Schema(description = "用户id", hidden = true)
    private Long memberId;
}
