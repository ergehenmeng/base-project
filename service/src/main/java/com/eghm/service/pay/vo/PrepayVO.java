package com.eghm.service.pay.vo;

import com.eghm.service.pay.enums.PayChannel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信/支付宝生成预支付信息
 *
 * @author 二哥很猛
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrepayVO {

    @ApiModelProperty("支付package")
    private String packageValue;

    @ApiModelProperty("签名方式")
    private String signType;

    @ApiModelProperty("签名信息")
    private String paySign;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("随机串")
    private String nonceStr;

    @ApiModelProperty("流水号")
    private String orderNo;

    @ApiModelProperty("h5支付url")
    private String h5Url;

    @ApiModelProperty("扫码支付url")
    private String qrCodeUrl;

    @ApiModelProperty("预支付id 微信app支付专用")
    private String prepayId;

    @ApiModelProperty("partnerId 微信app支付专用")
    private String partnerId;

    @ApiModelProperty("支付宝交易单号")
    private String outTradeNo;

    @ApiModelProperty("支付渠道 WECHAT:微信, ALIPAY:支付宝")
    @JsonIgnore
    private PayChannel payChannel;
}