package com.eghm.dto.business.coupon.user;

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
 * @date 2022/7/18
 */

@Data
public class GrantCouponDTO {

    @ApiModelProperty(value = "用户id列表")
    @NotEmpty(message = "用户id不能为空")
    private List<Long> userIdList;

    @ApiModelProperty("优惠券id")
    @NotNull(message = "优惠券id不能为空")
    private Long couponConfigId;

    @ApiModelProperty("发放数量")
    @RangeInt(min = 1, max = 99, message = "发放数量1~99")
    private Integer num;

}
