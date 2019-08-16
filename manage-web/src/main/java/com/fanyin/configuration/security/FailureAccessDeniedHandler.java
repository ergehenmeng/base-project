package com.fanyin.configuration.security;

import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.model.ext.RespBody;
import com.fanyin.utils.IpUtil;
import com.fanyin.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问权限不足处理器(用于前后端分离时,返回前台json)
 * @author 二哥很猛
 * @date 2019/7/10 14:33
 */
@Slf4j
public class FailureAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        log.warn("权限不足ip:[], url:[{}]", IpUtil.getIpAddress(request),request.getRequestURI());
        RespBody<Object> returnJson = RespBody.error(ErrorCodeEnum.ACCESS_DENIED);
        WebUtil.printJson(response, returnJson);
    }
}
