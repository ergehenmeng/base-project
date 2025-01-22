package com.eghm.vo.business.evaluation;

import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
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

    @ApiModelProperty(value = "显示状态 1:正常 2:屏蔽")
    private Integer state;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "是否匿名评论 0:非匿名1:匿名")
    private Boolean anonymity;

    @ApiModelProperty(value = "审核拒绝原因")
    private String auditRemark;

    @ApiModelProperty("审核人姓名")
    private String auditName;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
