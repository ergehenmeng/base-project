package com.eghm.dto.business.order.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class EvaluationDTO {

    @ApiModelProperty(value = "订单子表id")
    private Long orderId;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "物流评审1-5分")
    private Integer logisticsScore;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;
}
