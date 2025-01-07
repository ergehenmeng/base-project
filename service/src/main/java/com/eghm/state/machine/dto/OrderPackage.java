package com.eghm.state.machine.dto;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.ItemSpec;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/6
 */
@Data
public class OrderPackage {

    @Schema(description = "产品id")
    private Long itemId;

    @Schema(description = "产品skuId")
    private Long skuId;

    @Schema(description = "产品购买数量")
    private Integer num;

    @Schema(description = "商品一级的spu")
    private ItemSpec spec;

    @Schema(description = "产品信息")
    private Item item;

    @Schema(description = "sku信息")
    private ItemSku sku;

    @Schema(description = "真实销售单价(拼团时是拼团价,限时购时是限时购价格)")
    private Integer finalPrice;
}
