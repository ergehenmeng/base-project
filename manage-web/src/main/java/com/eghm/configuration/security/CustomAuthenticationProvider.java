package com.eghm.configuration.security;


import com.eghm.common.enums.ErrorCode;
import com.eghm.dao.model.system.SystemOperator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 自定义校验规则 authenticate校验图形验证码 super.authenticate方法会调用additionalAuthenticationChecks来校验密码信息
 *
 * @author 二哥很猛
 * @date 2018/1/25 13:48
 */
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    /**
     * 登陆密码加密 无需覆盖父类加密方式
     */
    private Encoder encoder;

    void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ImageCodeAuthenticationDetails principal = (ImageCodeAuthenticationDetails) authentication.getDetails();
        String imageCode = principal.getImageCode();
        String sessionImageCode = principal.getSessionImageCode();
        if (imageCode == null || !imageCode.equalsIgnoreCase(sessionImageCode)) {
            throw new SystemAuthenticationException(ErrorCode.IMAGE_CODE_ERROR);
        }
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        SystemOperator operator = (SystemOperator) userDetails;
        if (!encoder.matches((String) authentication.getCredentials(), operator.getPwd())) {
            throw new SystemAuthenticationException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
