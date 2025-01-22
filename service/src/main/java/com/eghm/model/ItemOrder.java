package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.DeliveryState;
import com.eghm.enums.DeliveryType;
import com.eghm.enums.ItemRefundState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-05
 */
@Data
@TableName("item_order")
@EqualsAndHashCode(callSuper = true)
public class ItemOrder extends BaseEntity {

    @Schema(description = "商品id")
    private Long itemId;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "退款状态 0:未退款 1:已退款")
    private ItemRefundState refundState;

    @Schema(description = "配送状态 0:初始 1:待发货 2:待收货 3:待自提 4:已收货")
    private DeliveryState deliveryState;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商品所属店铺")
    private Long storeId;

    @Schema(description = "商品描述信息")
    private String depict;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "订单所属用户ID(冗余)")
    private Long memberId;

    @Schema(description = "规格名称(多规格)")
    private String skuTitle;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "specId")
    private String specId;

    /**
     * skuPic优先级最高, specPic次之, 商品封面图第一张最低
     */
    @Schema(description = "sku封面图")
    private String skuCoverUrl;

    @Schema(description = "销售价格")
    private Integer salePrice;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "成本价")
    private Integer costPrice;

    @Schema(description = "快递费")
    private Integer expressFee;

    @Schema(description = "购买须知")
    private String purchaseNotes;

    @Schema(description = "限购数量")
    private Integer quota;

    @Schema(description = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @Schema(description = "商品介绍信息")
    private String introduce;

}
