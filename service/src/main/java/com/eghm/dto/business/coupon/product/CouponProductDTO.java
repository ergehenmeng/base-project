package com.eghm.dto.business.coupon.product;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class CouponProductDTO {

    @ApiModelProperty("商品id")
    @NotNull(message = "请选择商品")
    private Long productId;

    @ApiModelProperty("商品类型")
    @NotNull(message = "商品类型不能为空")
    private ProductType productType;
}
