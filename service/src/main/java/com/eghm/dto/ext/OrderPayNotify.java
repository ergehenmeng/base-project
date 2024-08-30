package com.eghm.dto.ext;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/8/30
 */

@Data
public class OrderPayNotify {

    @ApiModelProperty("支付金额")
    private Integer amount;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单类型")
    private ProductType productType;

    @ApiModelProperty("所属商户")
    private Long merchantId;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品所属门店id")
    private Long storeId;
}
