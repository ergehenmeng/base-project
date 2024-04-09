package com.eghm.vo.business.evaluation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationVO {

    @ApiModelProperty("规格名称(零售商品专用)")
    private String skuTitle;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @JsonIgnore
    @ApiModelProperty(value = "是否匿名评论 0:非匿名 1:匿名")
    private Boolean anonymity;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
}
