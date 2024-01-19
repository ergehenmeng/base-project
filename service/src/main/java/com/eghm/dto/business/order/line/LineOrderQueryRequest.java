package com.eghm.dto.business.order.line;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
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
public class LineOrderQueryRequest extends DatePagingComparator {

    @ApiModelProperty("线路订单状态")
    private Integer orderState;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty("是否使用优惠券")
    private Boolean useVoucher;

    @Assign
    @ApiModelProperty(hidden = true, value = "商户ID")
    private Long merchantId;
}
