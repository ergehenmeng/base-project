package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/30
 */

@Data
public class RefundApplyContext implements Context {

    @ApiModelProperty(value = "订单编号", required = true)
    private String orderNo;

    @ApiModelProperty(value = "申请退款金额(含快递费)", required = true)
    private Integer refundAmount;

    @ApiModelProperty(value = "退款原因", required = true)
    private String reason;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @ApiModelProperty("退款游客id(需要实名制时该字段不为空)")
    private List<Long> visitorIds;

    @Assign
    @ApiModelProperty(value = "退款数量(非零售时该字段必传)")
    private Integer num;

    @Assign
    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("产品类型")
    private ProductType productType;

    @ApiModelProperty("事件")
    private IEvent event;
}
