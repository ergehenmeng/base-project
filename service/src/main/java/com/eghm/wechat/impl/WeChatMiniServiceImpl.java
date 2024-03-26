package com.eghm.wechat.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.shortlink.GenerateShortLinkRequest;
import cn.binarywang.wx.miniapp.bean.urllink.GenerateUrlLinkRequest;
import com.eghm.configuration.SystemProperties;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.wechat.WeChatMiniService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2021/12/4 下午4:30
 */
@Slf4j
@Service("weChatMiniService")
@RequiredArgsConstructor
public class WeChatMiniServiceImpl implements WeChatMiniService {

    private WxMaService wxMaService;

    private final SystemProperties systemProperties;

    @Autowired(required = false)
    public void setWxMaService(WxMaService wxMaService) {
        this.wxMaService = wxMaService;
    }

    @Override
    public String authMobile(String jsCode) {
        this.verify();
        WxMaPhoneNumberInfo phoneNoInfo;
        try {
            phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(jsCode);
        } catch (WxErrorException e) {
            log.error("小程序授权手机号异常 [{}]", jsCode, e);
            throw new BusinessException(ErrorCode.MA_JS_AUTH);
        }
        if (phoneNoInfo == null) {
            log.error("小程序授权手机号为空 [{}]", jsCode);
            throw new BusinessException(ErrorCode.MA_AUTH_NULL);
        }
        return phoneNoInfo.getPhoneNumber();
    }

    @Override
    public String generateShortUrl(String pageUrl, String pageTitle, boolean persistent) {
        this.verify();
        GenerateShortLinkRequest request = GenerateShortLinkRequest.builder().pageUrl(pageUrl).pageTitle(pageTitle).isPermanent(persistent).build();
        try {
            return wxMaService.getLinkService().generateShortLink(request);
        } catch (WxErrorException e) {
            log.error("微信小程序生成短连接异常 [{}] [{}] [{}]", pageUrl, pageTitle, persistent, e);
            throw new BusinessException(ErrorCode.MA_SHORT_URL);
        }
    }

    @Override
    public String generateUrl(String pageUrl, String query, int validDay) {
        this.verify();
        SystemProperties.WeChatProperties wechat = systemProperties.getWechat();
        GenerateUrlLinkRequest request = GenerateUrlLinkRequest.builder().path(pageUrl).query(query).expireInterval(validDay).expireType(1).envVersion(wechat.getMiniVersion().getValue()).build();
        try {
            return wxMaService.getLinkService().generateUrlLink(request);
        } catch (WxErrorException e) {
            log.error("微信小程序生成加密连接异常 [{}] [{}] [{}]", pageUrl, query, validDay, e);
            throw new BusinessException(ErrorCode.MA_ENCRYPT_URL);
        }
    }

    /**
     * 校验小程序是否配置完整
     */
    private void verify() {
        if (wxMaService == null) {
            throw new BusinessException(ErrorCode.MA_NOT_CONFIG);
        }
    }
}
