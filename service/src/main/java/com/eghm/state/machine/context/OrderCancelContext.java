package com.eghm.state.machine.context;

import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class OrderCancelContext implements Context {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "源状态")
    private Integer from;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
