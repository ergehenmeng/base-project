package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 订单评价
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_evaluation")
@ApiModel(value="OrderEvaluation对象", description="订单评价")
public class OrderEvaluation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单子表id")
    private Long orderId;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "商品")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productTitle;

    @ApiModelProperty(value = "商品图片")
    private String productCover;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "物流评审1-5分")
    private Integer logisticsScore;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;

    @ApiModelProperty(value = "审核状态 0:待审核 1:审核通过 2:审核失败")
    private Integer state;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @ApiModelProperty(value = "审核拒绝原因")
    private String auditRemark;

}
