package com.eghm.model.dto.homestay.room.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author wyb
 * @date 2022/6/29 22:08
 */
@Data
public class RoomConfigQueryRequest {

    @DateTimeFormat(pattern = "yyyy-MM")
    @ApiModelProperty("月份 yyyy-MM")
    @NotNull(message = "月份不能为空")
    private LocalDate month;

    @ApiModelProperty("房型id")
    @NotNull(message = "房型id不能为空")
    private Long roomId;
}
