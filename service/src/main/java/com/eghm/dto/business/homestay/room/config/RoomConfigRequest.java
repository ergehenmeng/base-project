package com.eghm.dto.business.homestay.room.config;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.dto.ext.DateComparator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * 房型设置
 *
 * @author 二哥很猛
 * @since 2022/6/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoomConfigRequest extends DateComparator {

    @Schema(description = "周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择周期")
    private List<Integer> week;

    @Schema(description = "房型id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @Schema(description = "开始日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "截止日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "状态 false:不可用 true:可用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @Schema(description = "库存不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @Max(value = 9999, message = "最大库存9999")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @Schema(description = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

    @Schema(description = "民宿id", hidden = true)
    @Assign
    private Long homestayId;
}
