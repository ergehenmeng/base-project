package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付异步通知记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-26
 */
@Data
@TableName("pay_notify_log")
@EqualsAndHashCode(callSuper = true)
public class PayNotifyLog extends BaseEntity {

    @Schema(description = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @Schema(description = "异步通知唯一id")
    private String notifyId;

    @Schema(description = "通知类型 PAY: 支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "通知原始参数")
    private String params;

}
