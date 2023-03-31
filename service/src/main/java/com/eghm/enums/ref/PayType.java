package com.eghm.enums.ref;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/29
 */
@AllArgsConstructor
@Getter
public enum PayType {

    /**
     * APP
     */
    WECHAT_APP,

    /**
     * JSAPI支付 与小程序是通用的
     */
    WECHAT_JSAPI,

    /**
     * 微信小程序 与JSAPI是通用的
     */
    WECHAT_MINI,

    /**
     * 微信扫描支付
     */
    WECHAT_NATIVE,

    /**
     * H5支付 (微信外H5拉起支付)
     */
    WECHAT_H5,

    /**
     * 支付宝支付
     */
    ALI_PAY

}
