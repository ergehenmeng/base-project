package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.OptionInt;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemRefundApplyDTO {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("商品订单id")
    private Long orderId;

    @ApiModelProperty(value = "申请退款金额(含快递费)", required = true)
    @RangeInt(min = 1, max = 9999999, message = "退款金额不合法")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer applyAmount;

    @ApiModelProperty(value = "退款原因", required = true)
    private String reason;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款")
    @OptionInt(value = {1, 2}, message = "申请方式非法")
    private Integer applyType;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String expressCode;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String expressNo;

    @Assign
    @ApiModelProperty("用户id")
    private Long memberId;
}
