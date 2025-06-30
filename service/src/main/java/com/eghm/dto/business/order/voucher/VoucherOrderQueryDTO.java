package com.eghm.dto.business.order.voucher;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherOrderQueryDTO extends PagingQuery {

    @Schema(description = "订单状态 0:待支付 2:待使用 7:订单完成 8:已关闭")
    private OrderState state;

    @Schema(description = "用户id")
    @Assign
    private Long memberId;
}
