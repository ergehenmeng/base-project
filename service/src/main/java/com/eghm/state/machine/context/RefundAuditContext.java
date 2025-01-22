package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/3
 */

@Data
public class RefundAuditContext implements Context {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "退款记录id")
    private Long refundId;

    @Schema(description = "审核状态 1:通过 2:拒绝")
    private Integer state;

    @Schema(description = "实际退款金额")
    private Integer refundAmount;

    @Schema(description = "审批意见")
    private String auditRemark;

    @Schema(description = "审批人id", hidden = true)
    @Assign
    private Long auditUserId;

    @Schema(description = "源状态")
    private Integer from;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
