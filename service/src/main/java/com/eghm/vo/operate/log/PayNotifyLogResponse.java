package com.eghm.vo.operate.log;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.StepType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @Schema(description = "异步通知唯一id")
    private String notifyId;

    @Schema(description = "通知类型 PAY:支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "退款流水号")
    private String refundNo;

    @Schema(description = "通知原始参数")
    private String params;

    @Schema(description = "发送状态 0:未回放 1:回放成功")
    private Integer state;

    @Schema(description = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
