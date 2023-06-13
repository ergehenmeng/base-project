package com.eghm.vo.business.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/30
 */

@Data
public class MemberCouponCountVO {

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty("优惠券已领取数量")
    private Integer num;
}
