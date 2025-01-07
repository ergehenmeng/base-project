package com.eghm.dto.business.account.score;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/2/20
 */

@Data
public class ScoreRechargeDTO {

    @Schema(description = "商户id")
    @Assign
    private Long merchantId;

    @Schema(description = "充值金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入充值金额")
    private Integer amount;
}
