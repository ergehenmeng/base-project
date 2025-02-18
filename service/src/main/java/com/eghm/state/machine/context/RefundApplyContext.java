package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/30
 */

@Data
public class RefundApplyContext implements Context {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String orderNo;

    @Schema(description = "申请退款金额(含快递费)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer refundAmount;

    @Schema(description = "退款原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reason;

    @Schema(description = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @Schema(description = "退款游客id(需要实名制时该字段不为空)")
    private List<Long> visitorIds;

    @Assign
    @Schema(description = "退款数量(非零售时该字段必传)")
    private Integer num;

    @Assign
    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "源状态")
    private Integer from;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
