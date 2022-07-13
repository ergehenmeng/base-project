package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户优惠券表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_coupon")
@ApiModel(value="UserCoupon对象", description="用户优惠券表")
public class UserCoupon extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠券配置id")
    private Long couponConfigId;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "使用状态 0:未使用 1:已使用 2:已过期")
    private Integer state;

    @ApiModelProperty(value = "领取时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "使用时间")
    private LocalDateTime useTime;

}
