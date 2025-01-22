package com.eghm.vo.business.evaluation;

import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "订单编号")
    private String orderNo;

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

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @Schema(description = "审核拒绝原因")
    private String auditRemark;

    @Schema(description = "审核人姓名")
    private String auditName;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
