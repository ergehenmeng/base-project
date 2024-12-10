package com.eghm.wechat;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;

/**
 * 公众号
 *
 * @author 二哥很猛
 * @since 2021/12/4 下午3:18
 */
public interface WeChatMpService {

    /**
     * 第三方页面授权获取微信用户信息
     * 1. 根据jsCode获取openId
     * 2. 根据openId查询用户信息
     *
     * @param jsCode 页面授权jsCode
     * @return 微信用户信息
     */
    WxOAuth2UserInfo auth2(String jsCode);

    /**
     * 获取jsTicket相关信息
     *
     * @param url url
     * @return 签名信息
     */
    WxJsapiSignature jsTicket(String url);

    /**
     * 生成微信扫码登录链接
     *
     * @param state 唯一串
     * @return url
     */
    String qrConnectUrl(String state);

    /**
     * 第三方页面授权获取微信用户信息 (微信扫码回调)
     *
     * @param code code
     * @return openId
     */
    WxOAuth2AccessToken getAccessToken(String code);
}
