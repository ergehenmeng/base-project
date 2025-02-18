package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class LineRefundApplyDTO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "申请退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 5000000, message = "退款金额应小于50000元")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer refundAmount;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;

    @Schema(description = "退款游客id")
    private List<Long> visitorIds;
}
