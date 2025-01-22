package com.eghm.dto.business.coupon.member;

import com.eghm.annotation.Assign;
import com.eghm.enums.CouponMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Data
public class ReceiveCouponDTO {

    @Schema(description = "优惠券id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;

    @Schema(description = "领取数量", hidden = true)
    @Assign
    private Integer num;

    @Schema(description = "领取方式 1:页面领取 2:手动发放", hidden = true)
    @Assign
    private CouponMode mode;


}
