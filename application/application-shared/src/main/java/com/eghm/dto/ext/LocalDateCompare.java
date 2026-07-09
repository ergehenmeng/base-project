package com.eghm.dto.ext;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
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
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止日期", hidden = true)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
}
