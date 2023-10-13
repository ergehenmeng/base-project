package com.eghm.dto.business.order.evaluation;

import com.eghm.convertor.XssFilterDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

    @ApiModelProperty("景区评分1-5分")
    private Integer scenicScore;

    @ApiModelProperty("店铺评分1-5分(零售,线路,餐饮,民宿)")
    private Integer storeScore;

    @ApiModelProperty(value = "物流评分1-5分(零售)")
    private Integer logisticsScore;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "评论")
    @JsonDeserialize(using = XssFilterDecoder.class)
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;
}
