package com.eghm.vo.business.venue;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSitePriceVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @ApiModelProperty("当前日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nowDate;

    @ApiModelProperty("星期几 1-7")
    private Integer nowWeek;

    @ApiModelProperty("可预订数量 默认1")
    private Integer stock;

    @ApiModelProperty("是否可预定 0:不可预定 1:可预定")
    private Integer state;

    @ApiModelProperty(value = "价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer price;

}
