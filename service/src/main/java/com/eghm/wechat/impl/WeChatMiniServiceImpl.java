package com.eghm.wechat.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.wechat.WeChatMiniService;
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
public class WeChatMiniServiceImpl implements WeChatMiniService {

    private WxMaService wxMaService;

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

    /**
     * 校验小程序是否配置完整
     */
    private void verify() {
        if (wxMaService == null) {
            throw new BusinessException(ErrorCode.MA_NOT_CONFIG);
        }
    }
}
