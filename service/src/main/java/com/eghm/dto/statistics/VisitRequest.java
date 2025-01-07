package com.eghm.dto.statistics;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DateComparator;
import com.eghm.enums.SelectType;
import com.eghm.enums.ref.VisitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/22
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitRequest extends DateComparator {

    @Schema(description = "开始日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @Schema(description = "截止日期 yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @NotNull(message = "截止日期不能为空")
    private LocalDate endDate;

    @Schema(description = "访问类型")
    private VisitType visitType;

    @Schema(description = "查询类型")
    private SelectType selectType;
}
