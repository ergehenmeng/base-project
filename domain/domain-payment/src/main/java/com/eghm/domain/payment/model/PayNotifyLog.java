package com.eghm.domain.payment.model;

import com.eghm.domain.shared.model.BaseEntity;

import com.eghm.domain.payment.enums.PayChannel;
import com.eghm.domain.payment.enums.StepType;
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
@EqualsAndHashCode(callSuper = true)
public class PayNotifyLog extends BaseEntity {

    /** 交易方式 WECHAT:微信 ALIPAY:支付宝 */
    private PayChannel payChannel;

    /** 异步通知唯一id */
    private String notifyId;

    /** 通知类型 PAY: 支付异步通知 REFUND:退款异步通知 */
    private StepType stepType;

    /** 交易流水号 */
    private String tradeNo;

    /** 退款流水号 */
    private String refundNo;

    /** 通知原始参数 */
    private String params;

    /** 发送状态 0:未回放 1:回放成功 */
    private Integer state;

    public void initialize(PayChannel payChannel, String notifyId, StepType stepType, String tradeNo, String refundNo, String params) {
        this.payChannel = payChannel;
        this.notifyId = notifyId;
        this.stepType = stepType;
        this.tradeNo = tradeNo;
        this.refundNo = refundNo;
        this.params = params;
        this.state = 0;
    }

    public void markReplayed() {
        this.state = 1;
    }

    public boolean isReplayed() {
        return Integer.valueOf(1).equals(this.state);
    }
}
