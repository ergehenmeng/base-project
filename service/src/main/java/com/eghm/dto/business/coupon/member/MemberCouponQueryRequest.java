package com.eghm.dto.business.coupon.member;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotNull;

/**
 * 用户优惠券中心列表查询
 *
 * @author 二哥很猛
 * @since 2022/7/14
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberCouponQueryRequest extends PagingQuery {

    @Schema(description = "使用状态 0:未使用 1:已使用 2:已过期 ")
    @OptionInt(value = {0, 1, 2}, message = "使用状态非法", required = false)
    private Integer state;

    @Schema(description = "优惠券id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

}
