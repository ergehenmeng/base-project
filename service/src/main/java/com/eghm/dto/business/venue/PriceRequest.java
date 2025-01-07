package com.eghm.dto.business.venue;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class PriceRequest {

    @Schema(description = "开始时间(HH:mm)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "请选择开始时间")
    private LocalTime startTime;

    @Schema(description = "结束时间(HH:mm)", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "请选择结束时间")
    private LocalTime endTime;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入价格")
    private Integer price;
}
