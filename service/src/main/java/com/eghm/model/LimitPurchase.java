package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 限时购活动表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("limit_purchase")
public class LimitPurchase extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "提前预告小时")
    private Integer advanceHour;

}
