package com.eghm.vo.business.log;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付或退款请求记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Data
public class PayRequestLogResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "支付渠道 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "请求类型 PAY:支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "请求参数")
    private String requestBody;

    @Schema(description = "响应参数")
    private String responseBody;

    @Schema(description = "请求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
