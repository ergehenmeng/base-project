package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/3
 */

@Data
public class RefundAuditContext implements Context {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("退款记录id")
    private Long refundId;

    @ApiModelProperty("审核状态 1:通过 2:拒绝")
    private Integer state;

    @ApiModelProperty("实际退款金额")
    private Integer refundAmount;

    @ApiModelProperty("审批意见")
    private String auditRemark;

    @ApiModelProperty(value = "审批人id", hidden = true)
    @Assign
    private Long auditUserId;

    @ApiModelProperty("源状态")
    private Integer from;

}
