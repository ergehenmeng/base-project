package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 限时购商品表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("limit_purchase_item")
public class LimitPurchaseItem extends BaseEntity {

    @ApiModelProperty(value = "限时购活动id")
    private Long limitPurchaseId;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "零售id")
    private Long itemId;

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "优惠配置json")
    private String skuValue;

    @ApiModelProperty(value = "最大优惠金额")
    private Integer maxDiscountAmount;

    @ApiModelProperty(value = "开始预告时间")
    private LocalDateTime advanceTime;

}
