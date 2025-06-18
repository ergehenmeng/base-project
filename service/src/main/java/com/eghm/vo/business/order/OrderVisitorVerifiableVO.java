package com.eghm.vo.business.order;

import com.eghm.enums.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 支持核销的且包含游客信息的订单
 *
 * @author 二哥很猛
 * @since 2025/5/28
 */
@Data
public class OrderVisitorVerifiableVO {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:待收货 6:退款中 7:订单完成 8:已关闭 9:支付异常 10:退款异常 11:待成团")
    private OrderState state;

    @Schema(description = "核销码")
    private String verifyNo;

    @Schema(description = "游客信息")
    private List<VisitorVO> visitorList;
}
