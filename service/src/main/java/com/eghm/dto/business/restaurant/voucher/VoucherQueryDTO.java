package com.eghm.dto.business.restaurant.voucher;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/30 22:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherQueryDTO extends PagingQuery {

    @Schema(description = "餐饮商家id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "商家id不能为空")
    private Long restaurantId;

    @Schema(description = "按售价排序(1:正序 2:倒序)")
    private Integer sortByPrice;

    @Schema(description = "按销量排序(1:正序 2:倒序)")
    private Integer sortBySale;
}
