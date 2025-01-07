package com.eghm.dto.business.order.voucher;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
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

    @Schema(description = "餐饮订单状态")
    private Integer orderState;

    @Schema(description = "用户id")
    @Assign
    private Long memberId;
}
