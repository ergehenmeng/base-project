package com.eghm.vo.business.evaluation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationVO {

    @Schema(description = "规格名称(零售商品专用)")
    private String skuTitle;

    @Schema(description = "综合评分1-5分")
    private Integer score;

    @Schema(description = "评论")
    private String comment;

    @Schema(description = "评论图片")
    private String commentPic;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户昵称")
    private String nickName;

    @JsonIgnore
    @Schema(description = "是否匿名评论 0:非匿名 1:匿名")
    private Boolean anonymity;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
