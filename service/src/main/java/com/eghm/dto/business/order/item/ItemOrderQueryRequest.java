package com.eghm.dto.business.order.item;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.RefundState;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("门票订单状态")
    private OrderState orderState;

    @ApiModelProperty("退款状态")
    private RefundState refundState;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty("是否使用优惠券")
    private Boolean isVoucher;

    @ApiModelProperty("交付方式 0:无需发货 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @Assign
    @ApiModelProperty(hidden = true, value = "商户ID")
    private Long merchantId;
}
