package com.eghm.dto.business.coupon.config;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "优惠券类型 1:抵扣券 2:折扣券", required = true)
    private Integer couponType;
}
