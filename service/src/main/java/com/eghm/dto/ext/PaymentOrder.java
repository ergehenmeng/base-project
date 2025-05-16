package com.eghm.dto.ext;

import com.eghm.enums.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/5/16
 */
@Data
public class PaymentOrder {

    @ApiModelProperty(value = "交易单号")
    private String tradeNo;

    @ApiModelProperty(value = "支付渠道")
    private PayType payType;
}
