package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ref.DeliveryType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemOrderSnapshotVO {

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品所属店铺")
    private Long storeId;

    @Schema(description = "商品所属店铺")
    private String storeName;

    @Schema(description = "店铺logo")
    private String storeLogoUrl;

    @Schema(description = "商品描述信息")
    private String depict;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "规格名称(多规格)")
    private String skuTitle;

    @Schema(description = "封面图")
    private String skuCoverUrl;

    @Schema(description = "销售价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer linePrice;

    @Schema(description = "购买须知")
    private String purchaseNotes;

    @Schema(description = "交付方式 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @Schema(description = "商品介绍信息")
    private String introduce;
}
