package com.eghm.wechat.dto;

import lombok.Data;

/**
 * 微信公众号jsTicket签名信息.
 *
 * @author 二哥很猛
 */
@Data
public class JsTicketSignature {

    /**
     * 签名.
     */
    private String signature;

    /**
     * 时间戳.
     */
    private Long timestamp;

    /**
     * 随机字符串.
     */
    private String nonceStr;

    /**
     * 公众号appId.
     */
    private String appId;
}
