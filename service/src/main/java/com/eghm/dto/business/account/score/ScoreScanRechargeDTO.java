package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.pay.enums.PayChannel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreScanRechargeDTO {

    @Schema(description = "商户id", hidden = true)
    @Assign
    private Long merchantId;

    @Schema(description = "充值金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    @NotNull(message = "请输入充值金额")
    private Integer amount;

    @Schema(description = "充值方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择充值方式")
    private PayChannel payChannel;

    @Assign
    @Schema(description = "客户端ip", hidden = true)
    private String clientIp;
}
