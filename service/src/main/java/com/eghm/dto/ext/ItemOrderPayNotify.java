package com.eghm.dto.ext;

import com.eghm.enums.DeliveryType;
import com.eghm.enums.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/8/30
 */

@Data
public class ItemOrderPayNotify {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型")
    private ProductType productType;

    @ApiModelProperty(value = "配送方式")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "店铺id")
    private Long storeId;
}
