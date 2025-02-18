package com.eghm.state.machine.context;

import com.eghm.enums.ProductType;
import com.eghm.enums.event.IEvent;
import com.eghm.pay.vo.RefundVO;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/20
 */
@Data
public class RefundNotifyContext implements Context {

    @Schema(description = "支付流水号")
    private String tradeNo;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "退款结果")
    private RefundVO result;

    @Schema(description = "源状态")
    private Integer from;

    @Schema(description = "产品类型")
    private ProductType productType;

    @Schema(description = "事件")
    private IEvent event;
}
