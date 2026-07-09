package com.eghm.pay.model;

import com.eghm.model.BaseEntity;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
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
@EqualsAndHashCode(callSuper = true)
public class PayRequestLog extends BaseEntity {

    /** 交易方式 WECHAT:微信 ALIPAY:支付宝 */
    private PayChannel payChannel;

    /** 订单编号 */
    private String orderNo;

    /** 请求类型 PAY:支付异步通知 REFUND:退款异步通知 */
    private StepType stepType;

    /** 交易流水号 */
    private String tradeNo;

    /** 退款流水号 */
    private String refundNo;

    /** 请求参数 */
    private String requestBody;

    /** 响应参数 */
    private String responseBody;

    public void initialize(PayChannel payChannel, String orderNo, StepType stepType, String tradeNo, String refundNo) {
        this.payChannel = payChannel;
        this.orderNo = orderNo;
        this.stepType = stepType;
        this.tradeNo = tradeNo;
        this.refundNo = refundNo;
    }

    public void recordRequest(String requestBody, String responseBody) {
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }
}
