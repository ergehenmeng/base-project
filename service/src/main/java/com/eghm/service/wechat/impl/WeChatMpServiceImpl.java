package com.eghm.service.wechat.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.service.wechat.WeChatMpService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2021/12/4 下午4:30
 */
@Service("weChatMpService")
@Slf4j
public class WeChatMpServiceImpl implements WeChatMpService {

    private WxMpService wxMpService;

    @Autowired(required = false)
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public WxOAuth2UserInfo auth2(String jsCode) {
        this.verify();
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(jsCode);
            return wxMpService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");
        } catch (WxErrorException e) {
            log.error("微信网页授权异常 [{}]", jsCode, e);
            throw new BusinessException(ErrorCode.MP_JS_AUTH);
        }
    }

    /**
     * 校验微信公众号是否配置完整
     */
    private void verify() {
        if (wxMpService == null) {
            throw new BusinessException(ErrorCode.MP_NOT_CONFIG);
        }
    }
}
