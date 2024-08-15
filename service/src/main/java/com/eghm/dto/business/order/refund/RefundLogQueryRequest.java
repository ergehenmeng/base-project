package com.eghm.dto.business.order.refund;

import com.eghm.annotation.Assign;
import com.eghm.annotation.DateFormatter;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.AuditState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author wyb
 * @since 2023/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundLogQueryRequest extends PagingQuery {

    @ApiModelProperty("退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @ApiModelProperty("审核状态(0:待审核 1:审核通过 2:审核拒绝 3:取消审核)")
    private AuditState auditState;

    @ApiModelProperty(value = "开始日期")
    @DateFormatter(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "截止日期")
    @DateFormatter(pattern = "yyyy-MM-dd", offset = 1)
    private LocalDate endDate;

    @ApiModelProperty(value = "订单前置类型")
    private String prefix;

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;
}
