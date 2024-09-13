package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 目前支持取消退款的商品: 零售, 民宿, 线路, 门票
 * 餐饮券和场馆预约是直接退款, 不需要取消
 * @author wyb
 * @since 2023/6/1
 */
@Data
public class RefundCancelContext implements Context {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty(value = "游客id(民宿/线路/门票)")
    private Long visitorId;

    @ApiModelProperty(value = "零售订单id")
    private Long itemOrderId;

    @Assign
    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;
}
