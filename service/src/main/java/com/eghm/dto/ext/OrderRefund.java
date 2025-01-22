package com.eghm.dto.ext;

import com.eghm.enums.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/8
 */
@Data
public class OrderRefund {

    @Schema(description = "支付订单流水号")
    private String tradeNo;

    @Schema(description = "退款订单流水号")
    private String refundNo;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "支付方式")
    private PayType payType;
}
