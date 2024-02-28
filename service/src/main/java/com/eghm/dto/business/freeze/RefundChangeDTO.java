package com.eghm.dto.business.freeze;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
public class RefundChangeDTO {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "退款金额")
    private Integer refundAmount;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;
}
