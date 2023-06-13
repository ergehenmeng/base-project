package com.eghm.service.pay.vo;

import com.eghm.service.pay.enums.RefundChannel;
import com.eghm.service.pay.enums.RefundStatus;
import com.eghm.service.pay.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/24
 */
@Data
public class RefundVO {

    @ApiModelProperty("退款渠道")
    private RefundChannel channel;

    @ApiModelProperty("退款入账账户")
    private String channelAccount;

    @ApiModelProperty("退款状态")
    private RefundStatus state;

    @ApiModelProperty("本次退款金额")
    private Integer amount;

    @ApiModelProperty("累计退款成功金额(支付宝专用)")
    private Integer totalAmount;

    @ApiModelProperty("退款成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("退款受理时间")
    private LocalDateTime createTime;

    @ApiModelProperty("支付渠道 WECHAT:微信, ALI_PAY:支付宝, 其他属性不做处理")
    @JsonIgnore
    private TradeType tradeType;

}
