package com.eghm.vo.operate.log;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付异步通知记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-26
 */
@Data
public class PayNotifyLogResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @ApiModelProperty("异步通知唯一id")
    private String notifyId;

    @ApiModelProperty(value = "通知类型 PAY:支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;

    @ApiModelProperty(value = "退款流水号")
    private String refundNo;

    @ApiModelProperty(value = "通知原始参数")
    private String params;

    @ApiModelProperty("操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
