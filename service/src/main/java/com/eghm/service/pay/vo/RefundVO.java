package com.eghm.service.pay.vo;

import com.eghm.service.pay.enums.RefundChannel;
import com.eghm.service.pay.enums.RefundState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/7/24
 */
@Data
public class RefundVO {

    @ApiModelProperty("退款流水号(第三方)")
    private String refundId;

    @ApiModelProperty("退款渠道")
    private RefundChannel channel;

    @ApiModelProperty("退款入账账户")
    private String channelAccount;

    @ApiModelProperty("退款状态")
    private RefundState state;

    @ApiModelProperty("退款金额")
    private Integer amount;

    @ApiModelProperty("退款成功时间")
    private LocalDateTime successTime;

    @ApiModelProperty("退款受理时间")
    private LocalDateTime createTime;

}
