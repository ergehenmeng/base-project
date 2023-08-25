package com.eghm.dto.business.restaurant.voucher;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/6/30 22:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantVoucherQueryRequest extends PagingQuery {

    @ApiModelProperty("状态 0:待上架 1:已上架")
    private Integer state;

    @ApiModelProperty("餐饮商家id")
    private Long restaurantId;

    @ApiModelProperty(value = "商家id", hidden = true)
    @Assign
    private Long merchantId;
}
