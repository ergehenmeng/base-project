package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.enums.event.IEvent;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/10/12
 */

@Data
public class PlatformRefundRequest {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "申请退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 5000000, message = "退款金额应小于50000元")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @Schema(description = "退款数量(非实名制时该字段必填)")
    private Integer num;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;

    @Schema(description = "退款游客id(实名制时该字段必填)")
    private List<Long> visitorIds;

    @Assign
    @Schema(description = "触发退款类型", hidden = true)
    private IEvent event;
}
