package com.eghm.dto.business.account;

import com.eghm.enums.AccountType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class AccountDTO {

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("变动金额")
    private Integer amount;

    @ApiModelProperty("资金变动类型(1:订单收入 2:订单退款 3:积分提现收入 4:提现支出 5:积分充值支出)")
    private AccountType accountType;

    @ApiModelProperty("关联的交易单号(订单号或者提现单号)")
    private String tradeNo;

    @ApiModelProperty("备注信息")
    private String remark;
}
