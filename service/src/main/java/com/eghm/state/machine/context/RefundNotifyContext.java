package com.eghm.state.machine.context;

import com.eghm.pay.vo.RefundVO;
import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/20
 */
@Data
public class RefundNotifyContext implements Context {

    @ApiModelProperty("支付流水号")
    private String tradeNo;

    @ApiModelProperty("退款流水号")
    private String refundNo;

    @ApiModelProperty("退款结果")
    private RefundVO result;

    @ApiModelProperty("源状态")
    private Integer from;

}
