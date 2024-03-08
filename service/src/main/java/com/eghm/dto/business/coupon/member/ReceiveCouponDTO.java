package com.eghm.dto.business.coupon.member;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.CouponMode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
public class ReceiveCouponDTO {

    @ApiModelProperty(value = "优惠券id", required = true)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @ApiModelProperty(hidden = true, value = "用户id")
    @Assign
    private Long memberId;

    @ApiModelProperty(value = "领取数量", hidden = true)
    @Assign
    private Integer num;

    @ApiModelProperty(value = "领取方式 1:页面领取 2:手动发放", hidden = true)
    @Assign
    private CouponMode mode;


}
