package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "请求类型 PAY: 支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;

    @ApiModelProperty(value = "退款流水号")
    private String refundNo;

    @ApiModelProperty(value = "请求参数")
    private String requestBody;

    @ApiModelProperty(value = "响应参数")
    private String responseBody;

}
