package com.eghm.common.constant;

/**
 * 微信请求头信息
 * @author 二哥很猛
 * @date 2018/8/14 16:57
 */
public class WeChatHeader {

    private WeChatHeader() {
    }

    /**
     * 签名
     */
    public static final String SIGNATURE = "Wechatpay-Signature";

    /**
     * 时间戳
     */
    public static final String TIMESTAMP = "Wechatpay-Timestamp";

    /**
     * 证书序列号
     */
    public static final String SERIAL = "Wechatpay-Serial";

    /**
     * 随机串
     */
    public static final String NONCE = "Wechatpay-Nonce";

}
