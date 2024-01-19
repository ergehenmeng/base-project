package com.eghm.dto.business.restaurant.voucher;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/30 22:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "餐饮商家id", required = true)
    @NotNull(message = "商家id不能为空")
    private Long restaurantId;

    @ApiModelProperty("按售价排序(1:正序 2:倒序)")
    private Integer sortByPrice;

    @ApiModelProperty("按销量排序(1:正序 2:倒序)")
    private Integer sortBySale;
}
