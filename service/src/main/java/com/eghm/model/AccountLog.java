package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.AccountType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "资金变动类型(1:订单收入 2:订单退款 3:积分提现收入 4:提现支出 5:积分充值支出)")
    private AccountType accountType;

    @Schema(description = "变动金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @Schema(description = "1:收入 2:支出")
    private Integer direction;

    @Schema(description = "变动后的余额(可提现金额 + 支付冻结金额)")
    private Integer surplusAmount;

    @Schema(description = "关联的交易单号(订单号或者提现单号)")
    private String tradeNo;

    @Schema(description = "备注信息")
    private String remark;

}
