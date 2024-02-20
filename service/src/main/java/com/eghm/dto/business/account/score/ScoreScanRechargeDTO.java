package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.service.pay.enums.PayChannel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreScanRechargeDTO {

    @ApiModelProperty("商户id")
    @Assign
    private Long merchantId;

    @ApiModelProperty("充值金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @Min(value = 1000, message = "最低充值金额10元")
    private Integer amount;

    @ApiModelProperty("支付方式")
    private PayChannel payChannel;
}
