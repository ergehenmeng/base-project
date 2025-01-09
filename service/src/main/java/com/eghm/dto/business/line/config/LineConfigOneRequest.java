package com.eghm.dto.business.line.config;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/8/30
 */
@Data
public class LineConfigOneRequest {

    @Schema(description = "线路id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "线路id不能为空")
    private Long lineId;

    @Schema(description = "日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate configDate;

    @Schema(description = "状态 0:不可用 1:可用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否可定不能为空")
    private Boolean state;

    @Schema(description = "库存不能为空", requiredMode = Schema.RequiredMode.REQUIRED)
    @RangeInt(max = 9999, message = "最大库存9999")
    private Integer stock;

    @Schema(description = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @Schema(description = "销售价", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

}
