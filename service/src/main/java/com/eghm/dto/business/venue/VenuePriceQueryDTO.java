package com.eghm.dto.business.venue;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenuePriceQueryDTO {

    @Schema(description = "场次id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择场次")
    private Long venueSiteId;

    @Schema(description = "日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "请选择日期")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate nowDate;
}
