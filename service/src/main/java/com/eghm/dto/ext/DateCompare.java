package com.eghm.dto.ext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */

@Data
public class DateCompare {

    @ApiModelProperty("开始日期")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    private LocalDate endDate;
}
