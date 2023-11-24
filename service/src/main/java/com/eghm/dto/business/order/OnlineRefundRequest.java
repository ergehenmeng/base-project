package com.eghm.dto.business.order;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.OptionInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 强制退款
 * @author 二哥很猛
 * @since 2023/7/5
 */
@Data
public class OnlineRefundRequest {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "游客id", required = true)
    @NotEmpty(message = "请选择要退款的游客")
    private List<Long> visitorList;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款", required = true)
    @OptionInt(value = {1, 2}, message = "退款方式不合法")
    private Integer applyType;

    @ApiModelProperty(value = "退款金额", required = true)
    @Min(value = 1, message = "退款金额最少0.01元")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @ApiModelProperty(value = "备注信息", required = true)
    @NotBlank(message = "备注信息不能为空")
    private String remark;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String logisticsNo;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;
}
