package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "限时购活动id")
    private Long limitPurchaseId;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "零售id")
    private Long itemId;

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "优惠配置json")
    private String skuValue;

    @Schema(description = "最大优惠金额")
    private Integer maxDiscountAmount;

    @Schema(description = "开始预告时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime advanceTime;

}
