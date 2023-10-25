package com.eghm.dto.business.order.evaluation;

import com.eghm.convertor.XssFilterDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class EvaluationDTO {

    @ApiModelProperty(value = "订单子表id")
    private Long orderId;

    @ApiModelProperty("店铺评分1-5分(零售,线路,餐饮,民宿)")
    @RangeInt(min = 1, max = 5, message = "店铺评分不合法", required = false)
    private Integer storeScore;

    @ApiModelProperty(value = "物流评分1-5分(零售)")
    @RangeInt(min = 1, max = 5, message = "物流评分不合法", required = false)
    private Integer logisticsScore;

    @ApiModelProperty(value = "综合评分1-5分(零售,线路,餐饮,门票)")
    @RangeInt(min = 1, max = 5, message = "综合评分不合法", required = false)
    private Integer score;

    @ApiModelProperty(value = "评论")
    @JsonDeserialize(using = XssFilterDecoder.class)
    @Length(min = 5, max = 200, message = "评论信息应在5~200字符之间")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;
}
