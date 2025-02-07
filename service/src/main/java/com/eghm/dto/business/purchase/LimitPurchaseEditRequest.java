package com.eghm.dto.business.purchase;

import com.eghm.annotation.Assign;
import com.eghm.configuration.gson.LocalDateTimeAdapter;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class LimitPurchaseEditRequest {

    @Schema(description = "活动id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "活动id不能为空")
    private Long id;

    @Schema(description = "活动名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "活动名称不能为空")
    @Size(max = 20, message = "活动名称最大20字符")
    private String title;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "开始时间不能为空")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    @Schema(description = "提前预告小时", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 72, message = "提前预告不能大于72小时")
    private Integer advanceHour;

    @Schema(description = "商品列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择商品列表")
    private List<LimitSkuRequest> skuList;

    @Schema(description = "备注")
    @Size(max = 200, message = "备注最多200字符")
    private String remark;

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;
}
