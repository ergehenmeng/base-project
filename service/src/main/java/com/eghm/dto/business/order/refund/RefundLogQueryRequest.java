package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.configuration.gson.LocalDateAdapter;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.AuditState;
import com.google.gson.annotations.JsonAdapter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author wyb
 * @since 2023/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundLogQueryRequest extends PagingQuery {

    @Schema(description = "退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @Schema(description = "审核状态(0:待审核 1:审核通过 2:审核拒绝 3:取消审核)")
    private AuditState auditState;

    @Schema(description = "开始日期")
    @DateFormatter(pattern = "yyyy-MM-dd")
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    @JsonAdapter(LocalDateAdapter.class)
    private LocalDate endDate;

    @Schema(description = "订单前置类型")
    private String prefix;

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
