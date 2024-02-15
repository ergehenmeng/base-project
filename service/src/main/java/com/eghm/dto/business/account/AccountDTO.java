package com.eghm.dto.business.account;

import com.eghm.enums.ref.AccountType;
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

    @ApiModelProperty("账户类型")
    private AccountType accountType;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("交易流水号")
    private String tradeNo;

    @ApiModelProperty("备注信息")
    private String remark;
}
