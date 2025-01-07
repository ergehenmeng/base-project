package com.eghm.vo.business.venue;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/2/26
 */

@Data
public class VenuePhaseVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Schema(description = "价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;
}
