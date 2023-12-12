package com.eghm.service.wechat;

/**
 * 小程序
 * @author 二哥很猛
 * @date 2021/12/4 下午3:18
 */
public interface WeChatMiniService {

    /**
     * 微信小程序授权获取手机号
     * @param jsCode jsCode
     */
    String authMobile(String jsCode);
}
