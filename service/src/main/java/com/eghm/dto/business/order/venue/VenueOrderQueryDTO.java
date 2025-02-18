package com.eghm.dto.business.order.venue;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.AbstractDatePagingComparator;
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

    @ApiModelProperty("订单状态")
    private Integer state;

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
