package com.eghm.service.wechat;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

/**
 * 公众号
 * @author 二哥很猛
 * @date 2021/12/4 下午3:18
 */
public interface WeChatMpService {

    /**
     * 第三方页面授权获取微信用户信息
     * 1. 根据jsCode获取openId
     * 2. 根据openId查询用户信息
     * @param jsCode 页面授权jsCode
     * @return 微信用户信息
     */
    WxOAuth2UserInfo auth2(String jsCode);
}
