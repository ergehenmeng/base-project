package com.eghm.dto.business.order.ticket;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author wyb
 * @since 2023/6/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketOrderQueryRequest extends DatePagingComparator {

    @Schema(description = "订单状态")
    private OrderState state;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @Schema(description = "是否使用优惠券")
    private Boolean useVoucher;

    @Schema(description = "关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    private CloseType closeType;

    @Assign
    @Schema(description = "商户ID", hidden = true)
    private Long merchantId;
}
