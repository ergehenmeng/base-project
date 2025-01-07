package com.eghm.dto.business.order.item;

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
public class ItemOrderQueryDTO extends PagingQuery {

    @Schema(description = "订单状态 空:全部 1:待付款 2:待收货 3:待评价 4:售后订单")
    private Integer tabState;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
