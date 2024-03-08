package com.eghm.dto.business.coupon.product;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
public class CouponScopeRequest {

    @ApiModelProperty(value = "商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆", required = true)
    @NotNull(message = "商品类型不能为空")
    private ProductType productType;

    @ApiModelProperty(value = "商品id列表", required = true)
    @NotEmpty(message = "商品列表不能为空")
    private List<Long> productIds;
}
