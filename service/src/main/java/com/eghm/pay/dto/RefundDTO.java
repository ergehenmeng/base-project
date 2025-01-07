package com.eghm.pay.dto;

import com.eghm.pay.enums.TradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Data
public class RefundDTO {

    @Schema(description = "订单号", hidden = true)
    private String orderNo;

    @Schema(description = "商户支付流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tradeNo;

    @Schema(description = "商户退款流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refundNo;

    @Schema(description = "退款金额")
    private Integer amount;

    @Schema(description = "原支付金额")
    private Integer total;

    @Schema(description = "交易方式")
    private TradeType tradeType;

    @Schema(description = "退款原因")
    private String reason;
}
