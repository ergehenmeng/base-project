package com.eghm.dto.business.merchant;

import com.eghm.enums.WithdrawWay;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantWithdrawChangeRequest {

    @Schema(description = "提现方式 1:手动提现 2:自动提现", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择提现方式")
    private WithdrawWay withdrawWay;
}
