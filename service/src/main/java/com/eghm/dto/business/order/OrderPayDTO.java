package com.eghm.dto.business.order;

import com.eghm.pay.enums.TradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class OrderPayDTO {

    @Schema(description = "订单编号,多笔订单(购物车订单)逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "购买人id(openId或者buyerId)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "购买人id不能为空")
    private String buyerId;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "支付方式不能为空")
    private TradeType tradeType;
}
