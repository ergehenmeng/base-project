package com.eghm.state.machine.context;

import com.eghm.annotation.Assign;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;
import com.eghm.model.ItemOrder;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/30
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ItemRefundApplyContext extends RefundApplyContext implements Context {

    @Schema(description = "零售商品订单id")
    private Long itemOrderId;

    @Schema(description = "物流公司(退货退款)")
    private String expressCode;

    @Schema(description = "物流单号(退货退款)")
    private String expressNo;

    @Schema(description = "退款快递费")
    @Assign
    private Integer expressFee = 0;

    @Schema(description = "退款积分")
    @Assign
    private Integer scoreAmount = 0;

    @Schema(description = "退款商品信息(承载数据,减少数据库查询)")
    @Assign
    private ItemOrder itemOrder;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
