package com.eghm.dto.business.account.score;

import com.eghm.enums.ref.ChargeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/15
 */

@Data
public class ScoreAccountDTO {

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("变动积分")
    private Integer amount;

    @ApiModelProperty("账户类型(1:充值 2:支付收入 3:支付退款 4:抽奖支出 5:提现支出 6:关注赠送 7:提现失败)")
    private ChargeType chargeType;

    @ApiModelProperty("关联的交易单号(订单号或者提现单号)")
    private String tradeNo;
}
