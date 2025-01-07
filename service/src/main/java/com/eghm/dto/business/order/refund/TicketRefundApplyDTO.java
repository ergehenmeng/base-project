package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class TicketRefundApplyDTO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    /**
     * 因为有实名制门票和非实名制门票, 因此需要传退款数量和退款游客id
     */
    @Schema(description = "退款数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(min = 1, max = 99, message = "退款数量应为1~99")
    private Integer num;

    @Schema(description = "申请退款金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 5000000, message = "退款金额应小于50000元")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @Schema(description = "退款游客id")
    private List<Long> visitorIds;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;
}
