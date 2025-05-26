package com.eghm.dto.ext;

import com.eghm.enums.DeliveryType;
import com.eghm.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/8/30
 */

@Data
public class ItemOrderPayNotify {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单类型")
    private ProductType productType;

    @Schema(description = "配送方式")
    private DeliveryType deliveryType;

    @Schema(description = "所属商户")
    private Long merchantId;

    @Schema(description = "店铺id")
    private Long storeId;
}
