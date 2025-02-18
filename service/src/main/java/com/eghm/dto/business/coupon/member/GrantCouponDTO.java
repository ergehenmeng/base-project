package com.eghm.dto.business.coupon.member;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 系统发放
 *
 * @author 二哥很猛
 * @since 2022/7/18
 */

@Data
public class GrantCouponDTO {

    @Schema(description = "用户id列表")
    private List<Long> memberIds;

    @Schema(description = "优惠券id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @Schema(description = "标签id(与用户列表二选一该优先级次之)")
    private Long tagId;

    @Assign
    @Schema(description = "发放数量", hidden = true)
    private Integer num;
}
