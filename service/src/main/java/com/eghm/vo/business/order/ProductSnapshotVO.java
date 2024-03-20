package com.eghm.vo.business.order;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class ProductSnapshotVO {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单所属店铺(景区id,餐饮商家id, 旅行社id, 零售店铺id, 民宿id)")
    private Long storeId;

    @ApiModelProperty("订单号")
    private Integer orderNo;

    @ApiModelProperty(value = "商品")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productTitle;

    @ApiModelProperty("规格名称(只有零售商品有该字段)")
    private String skuTitle;

    @ApiModelProperty(value = "商品图片")
    private String productCover;

    @ApiModelProperty("订单类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;
}
