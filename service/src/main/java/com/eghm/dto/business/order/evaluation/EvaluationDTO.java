package com.eghm.dto.business.order.evaluation;

import com.eghm.convertor.XssEncoder;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class EvaluationDTO {

    @Schema(description = "订单子表id")
    private Long orderId;

    @Schema(description = "店铺评分1-5分(零售 线路 餐饮 民宿 场馆)")
    @RangeInt(min = 1, max = 5, message = "店铺评分不合法", required = false)
    private Integer storeScore;

    @Schema(description = "物流评分1-5分(零售)")
    @RangeInt(min = 1, max = 5, message = "物流评分不合法", required = false)
    private Integer logisticsScore;

    @Schema(description = "综合评分1-5分(零售 线路 餐饮 门票 场馆)")
    @RangeInt(min = 1, max = 5, message = "综合评分不合法", required = false)
    private Integer score;

    @Schema(description = "评论")
    @JsonDeserialize(using = XssEncoder.class)
    @Length(min = 5, max = 200, message = "评论信息应在5~200字符之间")
    @WordChecker(message = "评论信息包含敏感词")
    private String comment;

    @Schema(description = "评论图片")
    private String commentPic;
}
