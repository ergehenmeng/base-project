package com.eghm.dto.business.coupon.product;

import com.eghm.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/3/26
 */

@Data
public class CouponProductDTO {

    @Schema(description = "商品id")
    @NotNull(message = "请选择商品")
    private Long productId;

    @Schema(description = "商品类型")
    @NotNull(message = "商品类型不能为空")
    private ProductType productType;
}
