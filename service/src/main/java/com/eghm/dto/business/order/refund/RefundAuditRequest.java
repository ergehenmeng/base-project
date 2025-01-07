package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.OptionInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 退款审核
 *
 * @author wyb
 * @since 2023/6/9
 */
@Data
public class RefundAuditRequest {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "退款记录id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款id不能为空")
    private Long refundId;

    @Schema(description = "审核状态 1:通过 2:拒绝", requiredMode = Schema.RequiredMode.REQUIRED)
    @OptionInt(value = {1, 2}, message = "审核状态不合法")
    private Integer state;

    @Schema(description = "实际退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @Schema(description = "审批意见", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size( max = 100, message = "审批意见长度2~100字符")
    private String auditRemark;
}
