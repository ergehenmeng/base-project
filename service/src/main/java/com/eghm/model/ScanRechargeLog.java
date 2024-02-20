package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.service.pay.enums.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 扫码支付记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scan_recharge_log")
public class ScanRechargeLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "付款金额")
    private Integer amount;

    @ApiModelProperty(value = "付款状态 0:待支付 1:已支付 2:已过期")
    private Integer state;

    @ApiModelProperty(value = "二维码url")
    private String qrCode;

    @ApiModelProperty(value = "付款方式")
    private TradeType tradeType;

    @ApiModelProperty(value = "本地交易单号")
    private String tradeNo;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

}
