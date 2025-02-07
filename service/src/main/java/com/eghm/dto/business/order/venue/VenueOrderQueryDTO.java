package com.eghm.dto.business.order.venue;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VenueOrderQueryDTO extends AbstractDatePagingComparator {

    @Schema(description = "订单状态")
    private Integer state;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "用户id")
    @Assign
    private Long memberId;
}
