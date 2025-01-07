package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "提前预告小时")
    private Integer advanceHour;

    @Schema(description = "备注")
    private String remark;
}
