package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问权限不足处理器(用于前后端分离时,返回前台json)
 *
 * @author 二哥很猛
 * @date 2019/7/10 14:33
 */
@Slf4j
public class FailAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("权限不足ip:[{}], url:[{}]", IpUtil.getIpAddress(request), request.getRequestURI());
        RespBody<Void> returnJson = RespBody.error(ErrorCode.ACCESS_DENIED);
        WebUtil.printJson(response, returnJson);
    }
}
