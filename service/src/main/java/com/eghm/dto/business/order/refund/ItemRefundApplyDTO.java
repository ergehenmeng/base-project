package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemRefundApplyDTO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "商品订单id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择要退款的商品")
    private Long orderId;

    @Schema(description = "申请退款金额(含快递费)", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 5000000, message = "退款金额应小于50000元")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer refundAmount;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;

    @Schema(description = "申请方式 1:仅退款 2:退货退款", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "请选择申请方式")
    private Integer applyType;

    @Schema(description = "物流公司(退货退款)")
    private String expressCode;

    @Schema(description = "物流单号(退货退款)")
    private String expressNo;
}
