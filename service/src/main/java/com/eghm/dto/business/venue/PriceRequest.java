package com.eghm.dto.business.venue;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class PriceRequest {

    @ApiModelProperty(value = "开始时间(HH:mm)", required = true)
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "请选择开始时间")
    private LocalTime startTime;

    @ApiModelProperty(value = "结束时间(HH:mm)", required = true)
    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "请选择结束时间")
    private LocalTime endTime;

    @ApiModelProperty(value = "价格", required = true)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入价格")
    private Integer price;
}
