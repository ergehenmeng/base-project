package com.eghm.dto.business.order.ticket;

import com.eghm.annotation.Padding;
import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/14
 */
@Data
public class OfflineRefundRequest {

    @ApiModelProperty("订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("游客id")
    @NotEmpty(message = "请选择要退款的游客")
    private List<Long> visitorList;

    @ApiModelProperty("退款金额")
    @Min(value = 1, message = "退款金额最少0.01元")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @ApiModelProperty("退款凭证(转账记录)")
    @NotBlank(message = "退款凭证不能为空")
    private String certificate;

    @ApiModelProperty("备注信息")
    @NotBlank(message = "备注信息不能为空")
    private String remark;

    @Padding
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

}
