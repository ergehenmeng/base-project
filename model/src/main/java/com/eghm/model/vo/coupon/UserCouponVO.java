package com.eghm.model.vo.coupon;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/14
 */
@Data
public class UserCouponVO {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("优惠券名称")
    private String title;

    @ApiModelProperty("使用状态 0:未使用 1:已使用 2:已过期 ")
    private Integer state;

    @ApiModelProperty("金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

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
