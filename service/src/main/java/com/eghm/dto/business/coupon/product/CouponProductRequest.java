package com.eghm.dto.business.coupon.product;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
public class CouponProductRequest {

    @ApiModelProperty("商品类型")
    @NotNull(message = "商品类型不能为空")
    private ProductType productType;

    @ApiModelProperty("商品id列表")
    @NotEmpty(message = "商品列表不能为空")
    private List<Long> productIds;
}
