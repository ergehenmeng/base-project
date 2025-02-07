package com.eghm.dto.business.withdraw;

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
 * @since 2024/2/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WithdrawQueryRequest extends AbstractDatePagingComparator {

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "0:提现中 1:提现成功 2:提现失败")
    private Integer state;

    @Schema(description = "1:手动提现 2:自动提现")
    private Integer withdrawWay;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "结束日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
}
