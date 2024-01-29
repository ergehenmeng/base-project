package com.eghm.dto.business.limit;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class LimitPurchaseQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
