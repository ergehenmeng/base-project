package com.eghm.dto.business.order.homestay;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
import com.eghm.enums.OrderState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/11/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayOrderQueryRequest extends AbstractDatePagingComparator {

    @Schema(description = "门票订单状态")
    private OrderState orderState;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @Schema(description = "是否使用优惠券")
    private Boolean useVoucher;

    @Assign
    @Schema(description = "商户ID", hidden = true)
    private Long merchantId;
}
