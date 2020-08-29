package com.eghm.handler.email.service;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.template.HtmlTemplate;
import com.eghm.dao.model.business.EmailTemplate;
import com.eghm.handler.email.BaseEmailHandler;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.cache.CacheService;
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
        // key: bind_email_code::10086 value: 19280911
        cacheService.setValue(CacheConstant.BIND_EMAIL_CODE + userId, authCode);
        params.put("authCode", authCode);
        return htmlTemplate.render(template.getContent(), params);
    }
}
