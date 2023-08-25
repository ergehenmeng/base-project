package com.eghm.dto.business.coupon.member;

import com.eghm.enums.ref.CouponMode;
import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
public class ReceiveCouponDTO {

    @ApiModelProperty("优惠券id")
    @NotNull(message = "优惠券id不能为空")
    private Long couponConfigId;

    @ApiModelProperty(hidden = true, value = "用户id")
    @Assign
    private Long memberId;

    @ApiModelProperty(value = "领取数量", hidden = true)
    @Assign
    private Integer num;

    @ApiModelProperty(value = "领取方式", hidden = true)
    @Assign
    private CouponMode mode;


}
