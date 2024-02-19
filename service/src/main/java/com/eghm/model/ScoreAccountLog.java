package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ChargeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户积分变动明细表, 注意: 只有积分余额变动时才会记录, 订单支付或退款不记录, 只有订单完成时才会记录订单
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("score_account_log")
public class ScoreAccountLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "积分变动类型")
    private ChargeType chargeType;

    @ApiModelProperty(value = "变动积分")
    private Integer amount;

    @ApiModelProperty(value = "1:收入 2:支出")
    private Integer direction;

    @ApiModelProperty(value = "变动后的积分(可用积分)")
    private Integer surplusAmount;

    @ApiModelProperty("关联的交易单号(订单号或者提现单号)")
    private String tradeNo;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
