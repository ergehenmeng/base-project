package com.eghm.vo.operate.log;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "支付渠道 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "请求类型 PAY:支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;

    @ApiModelProperty(value = "退款流水号")
    private String refundNo;

    @ApiModelProperty(value = "请求参数")
    private String requestBody;

    @ApiModelProperty(value = "响应参数")
    private String responseBody;

    @ApiModelProperty("请求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
