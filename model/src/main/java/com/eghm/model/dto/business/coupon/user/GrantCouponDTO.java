package com.eghm.model.dto.business.coupon.user;

import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 系统发放
 *
 * @author 二哥很猛
 * @date 2022/7/18
 */

@Data
public class GrantCouponDTO {

    @ApiModelProperty("发放信息")
    @Size(message = "优惠券发放信息不能为空")
    private List<GrantConfig> configList;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Data
    public static class GrantConfig {

        @ApiModelProperty("优惠券id")
        @NotNull(message = "优惠券id不能为空")
        private Long couponConfigId;

        @ApiModelProperty("发放数量")
        @RangeInt(min = 1, max = 99, message = "发放数量1~99")
        private Integer num;

    }
}
