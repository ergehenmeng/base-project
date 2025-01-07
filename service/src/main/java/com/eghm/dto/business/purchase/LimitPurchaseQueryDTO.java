package com.eghm.dto.business.purchase;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LimitPurchaseQueryDTO extends PagingQuery {

    @Schema(description = "排序规则 0:默认排序 1:按价格排序 2:按销售量排序 3:好评率 4:优惠金额 ")
    private Integer sortBy = 0;

    @Schema(description = "商户id")
    private Long merchantId;
}
