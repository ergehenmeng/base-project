package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.pay.enums.TradeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @Schema(description = "付款状态 0:待支付 1:已支付 2:已过期 3: 支付失败")
    private Integer state;

    @Schema(description = "二维码url")
    private String qrCode;

    @Schema(description = "付款方式")
    private TradeType tradeType;

    @Schema(description = "本地交易单号")
    private String tradeNo;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

}
