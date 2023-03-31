package com.eghm.model.dto.business.coupon.user;

import com.eghm.common.annotation.Sign;
import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户优惠券中心列表查询
 * @author 二哥很猛
 * @date 2022/7/14
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCouponQueryPageDTO extends PagingQuery {

    @ApiModelProperty("使用状态 0:未使用 1:已使用 2:已过期 ")
    private Integer state;

    @ApiModelProperty("优惠券配置id")
    private Long couponConfigId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Sign
    private Long userId;

}
