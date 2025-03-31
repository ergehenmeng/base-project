package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 目前支持取消退款的商品: 零售, 民宿, 线路
 *
 * @author wyb
 * @since 2023/6/1
 */
@Data
public class ItemRefundCancelDTO {

    @Schema(description = "订单号")
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "退款订单id")
    @NotNull(message = "请选择要取消退款的商品")
    private Long itemOrderId;

    @Assign
    @Schema(description = "用户id", hidden = true)
    private Long memberId;
}
