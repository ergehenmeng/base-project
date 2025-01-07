package com.eghm.vo.business.limit;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class LimitPurchaseDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "提前预告小时")
    private Integer advanceHour;

    @Schema(description = "规格优惠列表")
    private List<LimitSkuResponse> skuList;

    @Schema(description = "商品id列表")
    private List<Long> itemIds;

    @Schema(description = "备注")
    private String remark;
}

