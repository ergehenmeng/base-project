package com.eghm.dto.business.coupon.user;

import com.eghm.enums.ref.CouponMode;
import com.eghm.annotation.Sign;
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
    @Sign
    private Long userId;

    @ApiModelProperty(value = "领取数量", hidden = true)
    @Sign
    private Integer num;

    @ApiModelProperty(value = "领取方式", hidden = true)
    @Sign
    private CouponMode mode;


}
