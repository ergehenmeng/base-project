package com.eghm.dto.business.venue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSessionAddRequest {

    @ApiModelProperty(value = "所属场馆id")
    @NotNull(message = "请选择所属场馆")
    private Long venueId;

    @ApiModelProperty(value = "开始时间(HH:mm)")
    private String startTime;

    @ApiModelProperty(value = "结束时间(HH:mm)")
    private String endTime;

    @ApiModelProperty(value = "价格")
    private Integer price;

}
