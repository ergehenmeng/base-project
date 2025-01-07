package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/10/19
 */

@Data
public class LocalDateCompare {

    @Schema(description = "开始日期", hidden = true)
    private LocalDate startDate;

    @Schema(description = "截止日期", hidden = true)
    private LocalDate endDate;
}
