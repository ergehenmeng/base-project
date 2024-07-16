package com.eghm.vo.business.coupon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 优惠券配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
@Data
public class CouponBaseResponse {

    @ApiModelProperty(value = "优惠券id")
    private Long id;

    @ApiModelProperty(value = "优惠券名称")
    private String title;

    @ApiModelProperty("状态 0:未启用 1:已启用")
    private Integer state;

}
