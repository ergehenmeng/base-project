package com.eghm.dto.business.venue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenuePriceQueryDTO {

    @ApiModelProperty(value = "场次id", required = true)
    @NotNull(message = "请选择场次")
    private Long venueSiteId;

    @ApiModelProperty(value = "日期 yyyy-MM-dd", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "请选择日期")
    private LocalDate nowDate;
}
