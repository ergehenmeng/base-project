package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单评价
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-28
 */
@Data
@TableName("order_evaluation")
@EqualsAndHashCode(callSuper = true)
public class OrderEvaluation extends BaseEntity {

    @ApiModelProperty(value = "订单子表id")
    private Long orderId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty(value = "商品类型")
    private ProductType productType;

    @ApiModelProperty(value = "商品")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productTitle;

    @ApiModelProperty("规格名称(零售商品专用)")
    private String skuTitle;

    @ApiModelProperty(value = "商品图片")
    private String productCover;

    @ApiModelProperty(value = "综合评分1-5分")
    private Integer score;

    @ApiModelProperty(value = "物流评分1-5分")
    private Integer logisticsScore;

    @ApiModelProperty(value = "店铺评分1-5分")
    private Integer storeScore;

    @ApiModelProperty(value = "评论")
    private String comment;

    @ApiModelProperty(value = "评论图片")
    private String commentPic;

    @ApiModelProperty(value = "审核状态 0:待审核 1:审核通过 2:审核失败")
    private Integer state;

    @ApiModelProperty("是否默认评价 0:不是默认 1:是默认评价")
    private Boolean systemEvaluate;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @ApiModelProperty(value = "审核拒绝原因")
    private String auditRemark;

    @ApiModelProperty("审核人id")
    private Long userId;

}
