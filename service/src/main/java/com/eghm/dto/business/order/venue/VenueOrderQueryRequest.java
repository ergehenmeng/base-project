package com.eghm.dto.business.order.venue;

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
public class VenueOrderQueryRequest extends DatePagingComparator {

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
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
