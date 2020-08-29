package com.eghm.handler.email.service;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.template.HtmlTemplate;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.business.EmailTemplate;
import com.eghm.handler.email.BaseEmailHandler;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.impl.SysConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 绑定邮箱时发送短信验证码
 * @author 殿小二
 * @date 2020/8/29
 */
@Service("bindEmailHandler")
public class BindEmailHandler extends BaseEmailHandler {

    private HtmlTemplate htmlTemplate;

    private CacheService cacheService;

    private SysConfigApi sysConfigApi;

    public static final String AUTH_CODE = "authCode";

    public static final String EMAIL = "email";

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setHtmlTemplate(HtmlTemplate htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    @Override
    protected String getContent(EmailTemplate template, SendEmail email) {
        Map<String, Object> params = email.getParams();
        String userId = params.get("userId").toString();
        String authCode = StringUtil.randomNumber(8);
        long expire = sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600);
        // 将本次发送验证码和接收的对象一并放入到缓存中
        cacheService.setHashValue(CacheConstant.BIND_EMAIL_CODE + userId, expire, AUTH_CODE, authCode);
        cacheService.setHashValue(CacheConstant.BIND_EMAIL_CODE + userId, expire, EMAIL, email.getEmail());
        params.put("authCode", authCode);
        return htmlTemplate.render(template.getContent(), params);
    }

}
