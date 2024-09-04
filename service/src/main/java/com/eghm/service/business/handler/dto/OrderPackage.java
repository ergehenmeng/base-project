package com.eghm.service.business.handler.dto;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ItemSpec;
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

    @ApiModelProperty("商品一级的spu")
    private ItemSpec spec;

    @ApiModelProperty("产品信息")
    private Item item;

    @ApiModelProperty("sku信息")
    private ItemSku sku;

    @ApiModelProperty("真实销售单价(拼团时是拼团价,限时购时是限时购价格)")
    private Integer finalPrice;
}
