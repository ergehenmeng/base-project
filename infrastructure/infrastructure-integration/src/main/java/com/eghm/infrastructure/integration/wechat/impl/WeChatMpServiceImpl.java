package com.eghm.infrastructure.integration.wechat.impl;

import com.eghm.infrastructure.integration.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.wechat.WeChatMpService;
import com.eghm.wechat.dto.JsTicketSignature;
import com.eghm.wechat.dto.MpAccessToken;
import com.eghm.wechat.dto.MpUserInfo;
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
@Slf4j
@RequiredArgsConstructor
@Service("weChatMpService")
public class WeChatMpServiceImpl implements WeChatMpService {

    private WxMpService wxMpService;

    private final SysConfigApi sysConfigApi;

    @Autowired(required = false)
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @Override
    public MpUserInfo auth2(String jsCode) {
        this.verify();
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(jsCode);
            return this.convert(wxMpService.getOAuth2Service().getUserInfo(accessToken, "zh_CN"));
        } catch (WxErrorException e) {
            log.error("微信网页授权异常 [{}]", jsCode, e);
            throw new BusinessException(ErrorCode.MP_JS_AUTH);
        }
    }

    @Override
    public JsTicketSignature jsTicket(String url) {
        this.verify();
        try {
            return this.convert(wxMpService.createJsapiSignature(url));
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
    public MpAccessToken getAccessToken(String code) {
        this.verify();
        try {
            return this.convert(wxMpService.getOAuth2Service().getAccessToken(code));
        } catch (WxErrorException e) {
            log.error("微信扫码回调获取openId异常 [{}]", code, e);
            throw new BusinessException(ErrorCode.MP_AUTH2_ERROR);
        }
    }

    /**
     * 转换微信公众号授权用户信息.
     *
     * @param userInfo 微信用户信息
     * @return 标准用户信息
     */
    private MpUserInfo convert(WxOAuth2UserInfo userInfo) {
        MpUserInfo info = new MpUserInfo();
        info.setOpenId(userInfo.getOpenid());
        info.setUnionId(userInfo.getUnionId());
        info.setNickname(userInfo.getNickname());
        info.setHeadImgUrl(userInfo.getHeadImgUrl());
        info.setSex(userInfo.getSex());
        return info;
    }

    /**
     * 转换微信公众号jsTicket签名信息.
     *
     * @param signature 微信签名信息
     * @return 标准签名信息
     */
    private JsTicketSignature convert(WxJsapiSignature signature) {
        JsTicketSignature ticket = new JsTicketSignature();
        ticket.setSignature(signature.getSignature());
        ticket.setTimestamp(signature.getTimestamp());
        ticket.setNonceStr(signature.getNonceStr());
        ticket.setAppId(signature.getAppId());
        return ticket;
    }

    /**
     * 转换微信公众号授权凭证.
     *
     * @param accessToken 微信授权凭证
     * @return 标准授权凭证
     */
    private MpAccessToken convert(WxOAuth2AccessToken accessToken) {
        MpAccessToken token = new MpAccessToken();
        token.setOpenId(accessToken.getOpenId());
        return token;
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
