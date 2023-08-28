package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class OrderEvaluationDTO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @ApiModelProperty("评价信息")
    private List<EvaluationDTO> commentList;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;
}
