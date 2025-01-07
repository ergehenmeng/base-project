package com.eghm.dto.business.order.item;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.RefundState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemOrderQueryRequest extends DatePagingComparator {

    @Schema(description = "门票订单状态")
    private OrderState orderState;

    @Schema(description = "退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @Schema(description = "开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @Schema(description = "是否使用优惠券")
    private Boolean useVoucher;

    @Schema(description = "订单类型 0:普通订单 1:限时购订单 2:拼团订单")
    private Integer orderType;

    @Assign
    @Schema(description = "商户ID", hidden = true)
    private Long merchantId;
}
