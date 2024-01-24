package com.eghm.service.business.handler.dto;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ItemSpec;
import com.eghm.model.ItemStore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/6
 */
@Data
public class OrderPackage {

    @ApiModelProperty("产品id")
    private Long itemId;

    @ApiModelProperty("产品skuId")
    private Long skuId;

    @ApiModelProperty("产品购买数量")
    private Integer num;

    @ApiModelProperty("商品所属店铺id")
    private Long storeId;

    /**
     * 注意, 单规格时, 该字段为null
     */
    @ApiModelProperty("商品一级的spu")
    private ItemSpec spec;

    @ApiModelProperty("产品信息")
    private Item item;

    @ApiModelProperty("sku信息")
    private ItemSku sku;

    @ApiModelProperty("零售店铺信息")
    private ItemStore itemStore;

    @ApiModelProperty("拼团id")
    private Long bookingId;
}
