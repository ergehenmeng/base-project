package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class OrderEvaluationDTO {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity = false;

    @ApiModelProperty(value = "评价信息", required = true)
    @NotEmpty(message = "评价信息不能为空")
    private List<EvaluationDTO> commentList;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;
}
