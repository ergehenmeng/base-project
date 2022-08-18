package com.eghm.model.dto.business.order.ticket;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.eghm.model.validation.annotation.OptionInt;
import com.eghm.model.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2022/8/3
 */

@Data
public class AuditTicketRefundRequest {

    @ApiModelProperty("订单编号")
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("退款记录id")
    @NotNull(message = "退款id不能为空")
    private Long refundId;

    @ApiModelProperty("审核状态 1:通过 2:拒绝")
    @OptionInt(value = {1, 2}, message = "审核状态不合法")
    private Integer state;

    @ApiModelProperty("实际退款金额")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @RangeInt(min = 1, max = 999999, message = "退款金额应为0.01~9999.99元之间", required = false)
    private Integer refundAmount;

    @ApiModelProperty("审批意见")
    @Size(min = 2, max = 100, message = "审批意见长度2~100字符")
    private String auditRemark;

}
