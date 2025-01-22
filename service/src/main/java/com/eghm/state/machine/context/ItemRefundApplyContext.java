package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/30
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemRefundApplyContext extends RefundApplyContext implements Context {

    @ApiModelProperty("零售商品订单id")
    private Long itemOrderId;

    @ApiModelProperty(value = "物流公司(退货退款)")
    private String expressCode;

    @ApiModelProperty(value = "物流单号(退货退款)")
    private String expressNo;

    @ApiModelProperty("退款快递费")
    @Assign
    private Integer expressFee = 0;

    @ApiModelProperty("退款积分")
    @Assign
    private Integer scoreAmount = 0;

    @ApiModelProperty("退款商品信息(承载数据,减少数据库查询)")
    @Assign
    private ItemOrder itemOrder;

    @ApiModelProperty("产品类型")
    private ProductType productType;

    @ApiModelProperty("事件")
    private IEvent event;
}
