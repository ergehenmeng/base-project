package com.eghm.model.dto.business.coupon.user;

import com.eghm.model.annotation.Sign;
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
}
