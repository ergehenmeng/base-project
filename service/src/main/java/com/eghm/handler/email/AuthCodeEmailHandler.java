package com.eghm.handler.email;

import com.eghm.cache.CacheService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.operate.email.SendEmail;
import com.eghm.model.EmailTemplate;
import com.eghm.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于发送邮箱验证码的基类handler
 *
 * @author 殿小二
 * @since 2020/9/3
 */
@Component("baseAuthCodeHandler")
public class AuthCodeEmailHandler extends BaseEmailHandler {

    public static final String AUTH_CODE = "authCode";

    public static final String EMAIL = "email";

    private CacheService cacheService;

    private SysConfigApi sysConfigApi;

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
        String memberId = params.get("memberId").toString();
        String authCode = StringUtil.randomNumber(8);
        long expire = sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600);
        // 将本次发送验证码和接收的对象一并放入到缓存中
        String cacheKey = email.getType().getValue() + ":" + memberId;
        cacheService.setHashValue(cacheKey, expire, AUTH_CODE, authCode);
        cacheService.setHashValue(cacheKey, expire, EMAIL, email.getEmail());
        params.put(AUTH_CODE, authCode);
        return params;
    }
}
