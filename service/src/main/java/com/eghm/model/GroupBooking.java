package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "零售id")
    private Long itemId;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "截止时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "拼团人数")
    private Integer num;

    @ApiModelProperty(value = "拼团有效期,单位:分钟")
    private Integer expireTime;

    @ApiModelProperty("sku拼团优惠json")
    private String skuValue;
}
