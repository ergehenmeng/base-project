package com.eghm.handler.email.service;

import com.eghm.cache.CacheService;
import com.eghm.common.EmailService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.handler.email.AuthCodeEmailHandler;
import org.springframework.stereotype.Component;

/**
 * 绑定邮箱时发送短信验证码 由父类实现验证码全部逻辑
 *
 * @author 殿小二
 * @since 2020/8/29
 */
@Component("bindEmailHandler")
public class BindEmailEmailHandler extends AuthCodeEmailHandler {

    public BindEmailEmailHandler(CacheService cacheService, SysConfigApi sysConfigApi, EmailService emailService, TemplateEngine templateEngine) {
        super(cacheService, sysConfigApi, emailService, templateEngine);
    }

}
