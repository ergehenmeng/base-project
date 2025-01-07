package com.eghm.dto.business.verify;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifyLogQueryRequest extends DatePagingComparator {

    @Schema(description = "开始时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止时间 yyyy-MM-dd")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
