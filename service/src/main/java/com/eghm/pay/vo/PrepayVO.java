package com.eghm.pay.vo;

import com.eghm.pay.enums.PayChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 微信/支付宝生成预支付信息
 *
 * @author 二哥很猛
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrepayVO {

    @Schema(description = "支付package")
    private String packageValue;

    @Schema(description = "签名方式")
    private String signType;

    @Schema(description = "签名信息")
    private String paySign;

    @Schema(description = "时间戳")
    private String timeStamp;

    @Schema(description = "随机串")
    private String nonceStr;

    @Schema(description = "流水号")
    private String orderNo;

    @Schema(description = "h5支付url")
    private String h5Url;

    @Schema(description = "扫码支付url(微信)")
    private String qrCodeUrl;

    @Schema(description = "扫码支付iframe(支付宝)")
    private String qrCodeHtml;

    @Schema(description = "预支付id 微信app支付专用")
    private String prepayId;

    @Schema(description = "partnerId 微信app支付专用")
    private String partnerId;

    @Schema(description = "支付宝交易单号")
    private String outTradeNo;

    @Schema(description = "支付渠道 WECHAT:微信, ALIPAY:支付宝")
    @JsonIgnore
    private PayChannel payChannel;
}