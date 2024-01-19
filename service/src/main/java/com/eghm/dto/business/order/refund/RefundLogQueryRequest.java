package com.eghm.dto.business.order.refund;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.AuditState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundLogQueryRequest extends PagingQuery {

    @ApiModelProperty("退款状态 0:退款中 1:退款成功 2:退款失败 3:取消退款")
    private Integer state;

    @ApiModelProperty("审核状态")
    private AuditState auditState;

    @ApiModelProperty(value = "退款申请时间开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "退款申请时间截止时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "订单前置类型", hidden = true)
    private String prefix;
}
