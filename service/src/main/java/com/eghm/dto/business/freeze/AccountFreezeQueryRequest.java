
package com.eghm.dto.business.freeze;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
import com.eghm.enums.ChangeType;
import com.eghm.enums.FreezeState;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountFreezeQueryRequest extends AbstractDatePagingComparator {

    @Schema(description = "状态 1:冻结中 2:已解冻")
    private FreezeState state;

    @Schema(description = "变更类型 1:支付冻结 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
