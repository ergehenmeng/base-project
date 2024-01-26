package com.eghm.dto.business.group;

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
public class GroupBookingQueryDTO extends PagingQuery {

    @ApiModelProperty("排序规则 0:默认排序 1: 按价格排序 2: 按销售量排序 3:好评率 4:优惠金额 ")
    private Integer sortBy = 0;

}
