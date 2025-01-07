package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/2/4
 */

@Data
public class VenuePhase {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Schema(description = "价格")
    private Integer price;
}
