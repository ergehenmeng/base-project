package com.eghm.dto.business.coupon.config;

import com.eghm.enums.ref.CouponType;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponQueryDTO extends PagingQuery {

    @ApiModelProperty("优惠券类型 1:抵扣券 2:折扣券")
    @NotNull(message = "优惠券类型不能为空")
    private CouponType couponType;
}
