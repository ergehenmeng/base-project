package com.eghm.service.pay.constant;

/**
 * @author 二哥很猛
 */
public class PayConstant {

    /**
     * 预支付订单生成
     */
    public static final String PREPAY_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    /**
     * 订单查询接口
     */
    public static final String QUERY_ORDER_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/%s?mchid=%s";

    private PayConstant() {
    }

}
