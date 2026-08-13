package com.eghm.foundation.core.dto.ext;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @Schema(description = "截止日期", hidden = true)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;
}
