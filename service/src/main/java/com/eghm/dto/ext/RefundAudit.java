package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/18
 */

@Data
public class RefundAudit {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商户ID")
    private Long merchantId;

    @Schema(description = "退款id不能为空")
    private Long refundId;

    @Schema(description = "实际退款金额")
    private Integer refundAmount;
}
