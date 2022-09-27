package com.eghm.handler.email;

import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.EmailTemplate;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.impl.SysConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于发送邮箱验证码的基类handler
 * @author 殿小二
 * @date 2020/9/3
 */
@Component("baseAuthCodeHandler")
public class AuthCodeEmailHandler extends BaseEmailHandler {

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

    @Override
    protected Map<String, Object> renderParams(EmailTemplate template, SendEmail email) {
        Map<String, Object> params = email.getParams();
        String userId = params.get("userId").toString();
        String authCode = StringUtil.randomNumber(8);
        long expire = sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600);
        // 将本次发送验证码和接收的对象一并放入到缓存中
        String cacheKey = email.getType().getValue() + "::" + userId;
        cacheService.setHashValue(cacheKey, expire, AUTH_CODE, authCode);
        cacheService.setHashValue(cacheKey, expire, EMAIL, email.getEmail());
        params.put("authCode", authCode);
        return params;
    }
}
