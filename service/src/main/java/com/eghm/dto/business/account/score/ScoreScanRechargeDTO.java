package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.pay.enums.PayChannel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreScanRechargeDTO {

    @ApiModelProperty(value = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @ApiModelProperty(value = "充值金额", required = true)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "请输入充值金额")
    private Integer amount;

    @ApiModelProperty(value = "充值方式", required = true)
    @NotNull(message = "请选择充值方式")
    private PayChannel payChannel;

    @Assign
    @ApiModelProperty(value = "客户端ip", hidden = true)
    private String clientIp;
}
