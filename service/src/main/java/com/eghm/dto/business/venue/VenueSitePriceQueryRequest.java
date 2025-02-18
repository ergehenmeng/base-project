package com.eghm.dto.business.venue;

import com.eghm.configuration.gson.LocalDateAdapter;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSitePriceQueryRequest {

    @Schema(description = "场次id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "场次id不能为空")
    private Long venueSiteId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "请选择日期")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate nowDate;
}
