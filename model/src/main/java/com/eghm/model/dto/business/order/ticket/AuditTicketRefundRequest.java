package com.eghm.model.dto.business.order.ticket;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2022/8/3
 */

@Data
public class AuditTicketRefundRequest {

    @ApiModelProperty("订单id")
    @NotNull(message = "订单id不能为空")
    private Long orderId;

    @ApiModelProperty("退款记录id")
    @NotNull(message = "退款id不能为空")
    private Long refundId;

    @ApiModelProperty("实际退款金额")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @ApiModelProperty("审批意见")
    @Size(min = 2, max = 100, message = "审批意见长度2~100字符")
    private String auditRemark;

}
