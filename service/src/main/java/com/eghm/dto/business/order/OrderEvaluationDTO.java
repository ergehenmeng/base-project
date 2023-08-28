package com.eghm.dto.business.order;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class OrderEvaluationDTO {

    @ApiModelProperty(value = "订单子表id")
    private Long orderId;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "物流评审1-5分")
    private Integer logisticsScore;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;
}
