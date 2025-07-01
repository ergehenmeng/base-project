package com.eghm.state.machine.context;

import com.eghm.pay.enums.TradeType;
import com.eghm.state.machine.Context;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class PayNotifyContext implements Context {

    @Schema(description = "支付流水号")
    private String tradeNo;

    @Schema(description = "支付方式")
    private TradeType tradeType;

    @Schema(description = "支付成功时间")
    private LocalDateTime successTime;

    @Schema(description = "源状态")
    private Integer from;

}
