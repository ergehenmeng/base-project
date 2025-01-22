package com.eghm.vo.business.order;

import com.eghm.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
public class ProductSnapshotVO {

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "订单所属店铺(景区id, 餐饮商家id, 旅行社id, 零售店铺id, 民宿id)")
    private Long storeId;

    @Schema(description = "订单号")
    private Integer orderNo;

    @Schema(description = "商品")
    private Long productId;

    @Schema(description = "商品名称")
    private String productTitle;

    @Schema(description = "规格名称(只有零售商品有该字段)")
    private String skuTitle;

    @Schema(description = "商品图片")
    private String productCover;

    @Schema(description = "订单类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;
}
