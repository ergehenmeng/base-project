package com.eghm.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.CouponState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("member_coupon")
@EqualsAndHashCode(callSuper = true)
public class MemberCoupon extends BaseEntity {

    @Schema(description = "优惠券id")
    private Long couponId;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "使用状态 0:未使用 1:已使用 2:已过期")
    private CouponState state;

    @Schema(description = "领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveTime;

    @Schema(description = "使用时间")
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

}
