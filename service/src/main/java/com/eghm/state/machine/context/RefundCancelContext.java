package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 目前支持取消退款的商品: 零售, 民宿, 线路, 门票
 * 餐饮券和场馆预约是直接退款, 不需要取消
 * @author wyb
 * @since 2023/6/1
 */
@Data
public class RefundCancelContext implements Context {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "游客id(民宿/线路/门票)")
    private Long visitorId;

    @Schema(description = "零售订单id")
    private Long itemOrderId;

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
