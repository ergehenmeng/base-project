package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.AccountType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户资金变动明细表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("account_log")
public class AccountLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "资金变动类型")
    private AccountType accountType;

    @ApiModelProperty(value = "变动金额")
    private Integer amount;

    @ApiModelProperty(value = "1:收入 2:支出")
    private Integer direction;

    @ApiModelProperty(value = "变动后的余额(可提现金额 + 支付冻结金额)")
    private Integer surplusAmount;

    @ApiModelProperty(value = "交易流水号")
    private String tradeNo;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
