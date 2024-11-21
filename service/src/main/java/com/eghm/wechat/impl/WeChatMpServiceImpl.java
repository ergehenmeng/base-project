package com.eghm.wechat.impl;

import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.wechat.WeChatMpService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2021/12/4 下午4:30
 */
@Service("weChatMpService")
@Slf4j
@RequiredArgsConstructor
public class WeChatMpServiceImpl implements WeChatMpService {

    private WxMpService wxMpService;

    private final SysConfigApi sysConfigApi;

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

    @Override
    public WxJsapiSignature jsTicket(String url) {
        this.verify();
        try {
            return wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            log.error("微信网页生成jsTicket异常 [{}]", url, e);
            throw new BusinessException(ErrorCode.MP_JS_TICKET);
        }
    }

    @Override
    public String qrConnectUrl(String state) {
        this.verify();
        String redirectUrl = sysConfigApi.getString(ConfigConstant.WECHAT_REDIRECT_URL);
        return wxMpService.buildQrConnectUrl(redirectUrl, "snsapi_login", state);
    }

    @Override
    public WxOAuth2AccessToken getAccessToken(String code) {
        this.verify();
        try {
            return wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("微信扫码回调获取openId异常 [{}]", code, e);
            throw new BusinessException(ErrorCode.MP_AUTH2_ERROR);
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
