package com.eghm.integration.payment.dto;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.integration.payment.enums.PayChannel;
import com.eghm.integration.payment.enums.StepType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayLogQueryRequest extends PagingQuery {

    @Schema(description = "请求类型 PAY:支付 REFUND:退款")
    private StepType stepType;

    @Schema(description = "支付渠道 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;
}
