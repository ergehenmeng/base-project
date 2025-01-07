package com.eghm.dto.business.restaurant.voucher;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/6/30 22:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherQueryRequest extends PagingQuery {

    @Schema(description = "状态 0:待上架 1:已上架")
    private Integer state;

    @Schema(description = "餐饮商家id")
    private Long restaurantId;

    @Schema(description = "商家id", hidden = true)
    @Assign
    private Long merchantId;
}
