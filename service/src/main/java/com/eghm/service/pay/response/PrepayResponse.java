package com.eghm.service.pay.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrepayResponse {

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

    @ApiModelProperty("预支付id app支付专用")
    private String prepayId;

    @ApiModelProperty("partnerId app支付专用")
    private String partnerId;
}