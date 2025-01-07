package com.eghm.vo.business.item;

import com.eghm.enums.ref.DeliveryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemDetailResponse {

    @Schema(description = "id(编辑时不能为空)")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "商品描述信息")
    private String depict;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "标签id")
    private List<String> tagList;

    @Schema(description = "是否为多规格商品 true:是 false:不是")
    private Boolean multiSpec;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "购买须知")
    private String purchaseNotes;

    @Schema(description = "限购数量")
    private Integer quota;

    @Schema(description = "交付方式 1:快递包邮 2:门店自提 ")
    private DeliveryType deliveryType;

    @Schema(description = "物流模板id(为空表示包邮)")
    private Long expressId;

    @Schema(description = "商品介绍信息")
    private String introduce;

    @Schema(description = "规格信息(单规格为空)")
    private List<ItemSpecResponse> specList;

    @Schema(description = "sku信息")
    private List<ItemSkuResponse> skuList;
}
