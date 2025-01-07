package com.eghm.dto.business.homestay.room.config;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@Data
public class RoomConfigEditRequest {

    @Schema(description = "房间id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房型id不能为空")
    private Long roomId;

    @Schema(description = "日期yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
    private LocalDate configDate;

    @Schema(description = "状态 false:不可用 true:可用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @Schema(description = "库存不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @Schema(description = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

    @Schema(description = "民宿id", hidden = true)
    @Assign
    private Long homestayId;
}
