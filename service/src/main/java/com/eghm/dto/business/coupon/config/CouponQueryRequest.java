package com.eghm.dto.business.coupon.config;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.CouponMode;
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
public class CouponQueryRequest extends PagingQuery {

    @ApiModelProperty("发放状态 0:未开始 1: 进行中 2: 已结束")
    @OptionInt(value = {0, 1, 2}, message = "发放状态非法", required = false)
    private Integer state;

    @ApiModelProperty("领取方式")
    private CouponMode mode;

    @ApiModelProperty(value = "是否只查询有库存的优惠券 true:是 false:查询全部", hidden = true)
    private Boolean inStock;
}
