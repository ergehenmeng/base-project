package com.eghm.dto.business.coupon.config;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponConfigQueryRequest extends PagingQuery {

    @ApiModelProperty("发放状态 0:未开始 1: 进行中 2: 已结束")
    private Integer state;

    @ApiModelProperty("发放方式 1:页面领取 2: 系统发放")
    @OptionInt(value = {1, 2}, required = false, message = "发放方式错误")
    private Integer mode;

    @ApiModelProperty(value = "是否只查询有库存的优惠券 true:是 false:查询全部", hidden = true)
    private Boolean inStock;
}
