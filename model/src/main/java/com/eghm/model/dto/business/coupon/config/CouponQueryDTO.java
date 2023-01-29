package com.eghm.model.dto.business.coupon.config;

import com.eghm.common.enums.ref.CouponType;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponQueryDTO extends PagingQuery {

    @ApiModelProperty("优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;
}
