package com.eghm.controller;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.business.LoginLog;
import com.eghm.model.ext.RespBody;
import com.eghm.model.ext.Token;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.common.TokenService;
import com.eghm.service.user.LoginLogService;
import com.eghm.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 * @date 2019/9/3 17:07
 */
@RestController
@Api("用户相关接口")
public class UserController {

    private TokenService tokenService;

    private LoginLogService loginLogService;

    private UserService userService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setLoginLogService(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @PostMapping("/user/set_password")
    public RespBody<Object> setPassword(){
        return RespBody.success();
    }

    /**
     * 查询上次用户设备登陆信息
     */
    @PostMapping("/user/last_login_device")
    public RespBody<LoginDeviceVO> lastLoginDevice(HttpServletRequest request) {
        String accessToken = request.getHeader(AppHeader.TOKEN);
        Token token = tokenService.getOfflineToken(accessToken);
        if (token == null) {
            // 用户强制登陆信息可能已过期
            return RespBody.success();
        }
        LoginLog lastLogin = loginLogService.getLastLogin(token.getUserId());
        return RespBody.success(LoginDeviceVO.builder().date(DateUtil.format(lastLogin.getAddTime(), "MM-dd HH:mm:ss")).deviceModel(lastLogin.getDeviceModel()).build());
    }

}
