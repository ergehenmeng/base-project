package com.fanyin.configuration.security;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.ext.RespBody;
import com.fanyin.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 校验失败处理方式 直接返回前台json
 * @author 二哥很猛
 * @date 2018/1/25 18:21
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
        if (exception instanceof SystemAuthenticationException){
            SystemAuthenticationException exc = (SystemAuthenticationException) exception;
            RespBody<Object> returnJson = RespBody.getInstance().setCode(exc.getCode()).setMsg(exc.getMessage());
            WebUtil.printJson(response, returnJson);
            return;
        }
        log.error("权限校验异常",exception);
        RespBody<Object> returnJson = RespBody.getInstance().error(ErrorCodeEnum.PERMISSION_ERROR);
        WebUtil.printJson(response, returnJson);
    }
}
