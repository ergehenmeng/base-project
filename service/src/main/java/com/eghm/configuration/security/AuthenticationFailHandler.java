package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证失败的处理器 直接返回前台json
 *
 * @author 二哥很猛
 * @date 2018/1/25 18:21
 */
@Slf4j
public class AuthenticationFailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Throwable cause = exception;
        if (exception instanceof InternalAuthenticationServiceException) {
            InternalAuthenticationServiceException exc = (InternalAuthenticationServiceException) exception;
            cause = exc.getCause();
        }
        if (cause instanceof SystemAuthenticationException) {
            SystemAuthenticationException exp = (SystemAuthenticationException) cause;
            RespBody<Object> returnJson = RespBody.error(exp.getCode(), exp.getMessage());
            WebUtil.printJson(response, returnJson);
            return;
        }
        log.error("授权校验异常", cause);
        RespBody<Object> returnJson = RespBody.error(ErrorCode.PERMISSION_ERROR);
        WebUtil.printJson(response, returnJson);
    }
}
