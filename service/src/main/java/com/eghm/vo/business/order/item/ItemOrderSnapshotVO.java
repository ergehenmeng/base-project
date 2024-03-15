package com.eghm.vo.business.order.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.DeliveryType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/9
 */

@Data
public class ItemOrderSnapshotVO {

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品所属店铺")
    private Long storeId;

    @ApiModelProperty(value = "商品所属店铺")
    private String storeName;

    @ApiModelProperty(value = "店铺logo")
    private String storeLogoUrl;

    @ApiModelProperty(value = "商品描述信息")
    private String depict;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "规格名称(多规格)")
    private String skuTitle;

    @ApiModelProperty(value = "封面图")
    private String skuCoverUrl;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "交付方式 0:无需发货 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;
}
