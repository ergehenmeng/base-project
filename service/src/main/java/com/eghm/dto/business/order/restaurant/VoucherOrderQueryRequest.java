package com.eghm.dto.business.order.restaurant;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherOrderQueryRequest extends PagingQuery {

    @ApiModelProperty("餐饮订单状态")
    private Integer orderState;

    @ApiModelProperty("用户id")
    @Assign
    private Long memberId;
}
