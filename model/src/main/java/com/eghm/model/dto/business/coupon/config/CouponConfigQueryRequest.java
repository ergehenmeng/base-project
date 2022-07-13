package com.eghm.model.dto.business.coupon.config;

import com.eghm.model.dto.ext.PagingQuery;
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

}
