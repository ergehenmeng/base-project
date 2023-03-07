package com.eghm.service.business.handler.dto;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2022/9/6
 */
@Data
public class OrderPackage {

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("产品skuId")
    private Long skuId;

    @ApiModelProperty("产品购买数量")
    private Integer num;

    @ApiModelProperty("商品所属店铺id")
    private Long storeId;

    @ApiModelProperty("产品信息")
    private Item item;

    @ApiModelProperty("sku信息")
    private ItemSku sku;
}
