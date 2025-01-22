package com.eghm.vo.business.venue;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSitePriceVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Schema(description = "当前日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nowDate;

    @Schema(description = "可预订数量 默认1")
    private Integer stock;

    @Schema(description = "是否可预定 false:不可预定 true:可预定")
    private Boolean state;

    @Schema(description = "价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer price;

}
