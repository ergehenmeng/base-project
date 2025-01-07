package com.eghm.pay.vo;

import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.RefundChannel;
import com.eghm.pay.enums.RefundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Data
public class RefundVO {

    @Schema(description = "退款渠道")
    private RefundChannel channel;

    @Schema(description = "退款入账账户")
    private String channelAccount;

    @Schema(description = "退款状态")
    private RefundStatus state;

    @Schema(description = "本次退款金额")
    private Integer amount;

    @Schema(description = "累计退款成功金额(支付宝专用)")
    private Integer totalAmount;

    @Schema(description = "退款成功时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime successTime;

    @Schema(description = "退款受理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "支付渠道 WECHAT:微信, ALIPAY:支付宝, 其他属性不做处理")
    @JsonIgnore
    private PayChannel payChannel;

}
