package com.eghm.dto.business.coupon.member;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户优惠券中心列表查询
 *
 * @author 二哥很猛
 * @since 2022/7/14
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberCouponQueryPageDTO extends PagingQuery {

    @ApiModelProperty("使用状态 0:未使用 1:已使用 2:已过期 ")
    @OptionInt(value = {0, 1, 2}, message = "使用状态非法", required = false)
    private Integer state;

    @ApiModelProperty("优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Assign
    private Long memberId;

}
