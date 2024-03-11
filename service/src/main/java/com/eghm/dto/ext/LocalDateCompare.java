package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */

@Data
public class LocalDateCompare {

    @ApiModelProperty(value = "开始日期", hidden = true)
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期", hidden = true)
    private LocalDate endDate;
}
