package com.eghm.service.business.handler.context;

import com.eghm.annotation.Assign;
import com.eghm.model.ItemOrder;
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

    @ApiModelProperty(value = "退款数量", required = true)
    private Integer num;

    @ApiModelProperty("普通商品订单id")
    private Long itemOrderId;

    @ApiModelProperty(value = "申请退款金额(含快递费)", required = true)
    private Integer refundAmount;

    @ApiModelProperty(value = "退款原因", required = true)
    private String reason;

    @ApiModelProperty(value = "申请方式 1:仅退款 2:退货退款")
    private Integer applyType;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String expressCode;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String expressNo;

    @ApiModelProperty("退款游客id")
    private List<Long> visitorIds;

    @Assign
    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("退款快递费")
    @Assign
    private Integer expressFee = 0;

    @ApiModelProperty("退款积分")
    @Assign
    private Integer scoreAmount = 0;

    @ApiModelProperty("退款商品信息")
    @Assign
    private ItemOrder itemOrder;
}
