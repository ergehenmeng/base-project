package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 拼团活动表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group_booking")
public class GroupBooking extends BaseEntity {

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "零售id")
    private Long itemId;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "拼团人数")
    private Integer num;

    @Schema(description = "最大拼团优惠金额")
    private Integer maxDiscountAmount;

    @Schema(description = "拼团有效期,单位:分钟")
    private Integer expireTime;

    @Schema(description = "sku拼团优惠json")
    private String skuValue;

    @Schema(description = "商户id")
    private Long merchantId;
}
