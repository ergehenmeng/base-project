package com.eghm.dto.business.verify;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.DatePagingComparator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VerifyLogQueryRequest extends DatePagingComparator {

    @ApiModelProperty("开始时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
