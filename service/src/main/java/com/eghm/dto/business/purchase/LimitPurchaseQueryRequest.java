package com.eghm.dto.business.purchase;

import com.eghm.annotation.Assign;
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
public class LimitPurchaseQueryRequest extends PagingQuery {

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;
}
