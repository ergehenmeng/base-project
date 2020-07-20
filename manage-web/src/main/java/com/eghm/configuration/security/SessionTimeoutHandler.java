package com.eghm.configuration.security;

import com.eghm.common.enums.ErrorCode;
import com.eghm.model.ext.RespBody;
import com.eghm.utils.IpUtil;
import com.eghm.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 殿小二
 * @date 2020/7/20
 */
@Slf4j
public class SessionTimeoutHandler implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("用户session已过期 ip:[{}]", IpUtil.getIpAddress(request));
        WebUtil.printJson(response, RespBody.error(ErrorCode.SESSION_TIMEOUT));
    }
}
