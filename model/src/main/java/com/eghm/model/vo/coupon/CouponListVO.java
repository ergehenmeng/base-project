package com.eghm.model.vo.coupon;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.eghm.common.enums.ref.CouponType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */

@Data
public class CouponListVO {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String title;

    @ApiModelProperty("是否已领取 true:已领取 false:未领取")
    private Boolean received = false;

    @ApiModelProperty(value = "领取上限", hidden = true)
    @JsonIgnore
    private Integer maxLimit;

    @ApiModelProperty("优惠券类型 1:抵扣券 2:折扣券")
    private CouponType couponType;

    @ApiModelProperty("抵扣金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer deductionValue;

    @ApiModelProperty(value = "折扣比例 1-100")
    private Integer discountValue;

    @ApiModelProperty("使用门槛, 0:表示不限制")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer useThreshold;

    @ApiModelProperty("有效期开始时间")
    private LocalDateTime useStartTime;

    @ApiModelProperty("有效期截止时间")
    private LocalDateTime useEndTime;

    @ApiModelProperty("使用说明")
    private String instruction;
}
