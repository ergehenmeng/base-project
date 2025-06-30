package com.eghm.dto.business.order.venue;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
import com.eghm.enums.OrderState;
import com.eghm.enums.RefundState;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "订单状态 0:待支付 2:待使用 7:订单完成 8:已关闭")
    private OrderState state;

    @ApiModelProperty(value = "退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    private RefundState refundState;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty("用户id")
    @Assign
    private Long memberId;
}
