package com.eghm.model.vo.coupon;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 优惠券基础信息
 * @author 二哥很猛
 * @date 2022/7/14
 */
@Data
public class UserCouponBaseVO {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String title;

    @ApiModelProperty("金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @ApiModelProperty("使用门槛, 0:表示不限制")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer useThreshold;

}
