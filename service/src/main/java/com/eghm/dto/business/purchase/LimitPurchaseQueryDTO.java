package com.eghm.dto.business.purchase;

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
public class LimitPurchaseQueryDTO extends PagingQuery {

    @ApiModelProperty("排序规则 0:默认排序 1:按价格排序 2:按销售量排序 3:好评率 4:优惠金额 ")
    private Integer sortBy = 0;

    @ApiModelProperty("商户id")
    private Long merchantId;
}
