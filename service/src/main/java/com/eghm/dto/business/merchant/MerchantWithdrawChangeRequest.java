package com.eghm.dto.business.merchant;

import com.eghm.enums.WithdrawWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/16
 */

@Data
public class MerchantWithdrawChangeRequest {

    @ApiModelProperty(value = "提现方式 1:手动提现 2:自动提现", required = true)
    @NotNull(message = "请选择提现方式")
    private WithdrawWay withdrawWay;
}
