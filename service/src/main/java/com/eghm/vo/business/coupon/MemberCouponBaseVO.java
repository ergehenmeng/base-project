package com.eghm.vo.business.coupon;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.CouponType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 优惠券基础信息
 *
 * @author 二哥很猛
 * @since 2022/7/14
 */
@Data
public class MemberCouponBaseVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "优惠券名称")
    private String title;

    @Schema(description = "优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @Schema(description = "抵扣金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer deductionValue;

    @Schema(description = "折扣比例 1-100")
    private Integer discountValue;

    @Schema(description = "使用门槛, 0:表示不限制")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer useThreshold;

}
