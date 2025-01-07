package com.eghm.dto.business.freeze;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
public class RefundChangeDTO {

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "退款金额")
    private Integer refundAmount;

    @Schema(description = "订单编号")
    private String orderNo;
}
