package com.eghm.dto.ext;

import com.eghm.enums.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2025/5/16
 */
@Data
public class PaymentOrder {

    @Schema(description = "交易单号")
    private String tradeNo;

    @Schema(description = "支付渠道")
    private PayType payType;
}
