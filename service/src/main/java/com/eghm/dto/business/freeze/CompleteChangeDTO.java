package com.eghm.dto.business.freeze;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
public class CompleteChangeDTO {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "解冻金额")
    private Integer amount;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;
}
