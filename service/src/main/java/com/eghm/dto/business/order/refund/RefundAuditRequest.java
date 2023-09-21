package com.eghm.dto.business.order.refund;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 退款审核
 * @author wyb
 * @since 2023/6/9
 */
@Data
public class RefundAuditRequest {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "退款记录id", required = true)
    @NotNull(message = "退款id不能为空")
    private Long refundId;

    @ApiModelProperty(value = "审核状态 1:通过 2:拒绝", required = true)
    @OptionInt(value = {1, 2}, message = "审核状态不合法")
    private Integer state;

    @ApiModelProperty(value = "实际退款金额", required = true)
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @RangeInt(min = 1, max = 999999, message = "退款金额应为0.01~9999.99元之间", required = false)
    private Integer refundAmount;

    @ApiModelProperty(value = "审批意见", required = true)
    @Size(min = 2, max = 100, message = "审批意见长度2~100字符")
    @NotBlank(message = "审批意见不能为空")
    private String auditRemark;
}
