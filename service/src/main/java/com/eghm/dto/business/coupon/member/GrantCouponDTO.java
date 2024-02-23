package com.eghm.dto.business.coupon.member;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统发放
 *
 * @author 二哥很猛
 * @since 2022/7/18
 */

@Data
public class GrantCouponDTO {

    @ApiModelProperty(value = "用户id列表", required = true)
    @NotEmpty(message = "用户id不能为空")
    private List<Long> memberIdList;

    @ApiModelProperty(value = "优惠券id", required = true)
    @NotNull(message = "优惠券id不能为空")
    private Long couponId;

    @ApiModelProperty(value = "发放数量", required = true)
    @RangeInt(min = 1, max = 99, message = "发放数量1~99")
    private Integer num;

}
