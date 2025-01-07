package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付或退款请求记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Data
@TableName("pay_request_log")
@EqualsAndHashCode(callSuper = true)
public class PayRequestLog extends BaseEntity {

    @Schema(description = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "请求类型 PAY: 支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "请求参数")
    private String requestBody;

    @Schema(description = "响应参数")
    private String responseBody;

}
