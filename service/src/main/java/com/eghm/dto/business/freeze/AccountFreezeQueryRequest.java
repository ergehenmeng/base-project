
package com.eghm.dto.business.freeze;

import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.DatePagingComparator;
import com.eghm.enums.ref.ChangeType;
import com.eghm.enums.ref.FreezeState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountFreezeQueryRequest extends DatePagingComparator {

    @ApiModelProperty(value = "状态 1:冻结中 2:已解冻")
    private FreezeState state;

    @ApiModelProperty(value = "状态变更原因 1:支付冻结 2:退款解冻 3:订单完成解冻")
    private ChangeType changeType;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty("截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long merchantId;
}
