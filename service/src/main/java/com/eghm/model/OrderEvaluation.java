package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "订单子表id")
    private Long orderId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "商品")
    private Long productId;

    @Schema(description = "商品名称")
    private String productTitle;

    @Schema(description = "规格名称(零售商品专用)")
    private String skuTitle;

    @Schema(description = "商品图片")
    private String productCover;

    @Schema(description = "综合评分1-5分")
    private Integer score;

    @Schema(description = "物流评分1-5分")
    private Integer logisticsScore;

    @Schema(description = "店铺评分1-5分")
    private Integer storeScore;

    @Schema(description = "评论")
    private String comment;

    @Schema(description = "评论图片")
    private String commentPic;

    @Schema(description = "显示状态 1:正常 2:屏蔽")
    private Integer state;

    @Schema(description = "是否默认评价 0:不是默认 1:是默认评价")
    private Boolean systemEvaluate;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @Schema(description = "审核拒绝原因")
    private String auditRemark;

    @Schema(description = "审核人id")
    private Long userId;

}
