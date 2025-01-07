package com.eghm.dto.business.group;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2024/1/23
 */

@Data
public class GroupBookingAddRequest {

    @Schema(description = "活动名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "活动名称不能为空")
    @Size(max = 20, message = "活动名称最大20字符")
    private String title;

    @Schema(description = "零售id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择商品")
    private Long itemId;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "拼团人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "拼团人数不能为空")
    @RangeInt(min = 2, max = 99, message = "拼团人数必须在2-99之间")
    private Integer num;

    @Schema(description = "拼团有效期,单位:分钟", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "拼团有效期不能为空")
    private Integer expireTime;

    @Schema(description = "sku拼团优惠json", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "拼团优惠不能为空")
    private List<GroupItemSkuRequest> skuList;

    @Assign
    @Schema(description = "商户id", hidden = true)
    private Long merchantId;
}
