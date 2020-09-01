package com.eghm.controller;

import com.eghm.common.constant.AppHeader;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.business.LoginLog;
import com.eghm.model.dto.user.BindEmailRequest;
import com.eghm.model.dto.user.SendAuthCodeRequest;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.model.ext.RespBody;
import com.eghm.model.ext.Token;
import com.eghm.model.vo.user.LoginDeviceVO;
import com.eghm.service.common.TokenService;
import com.eghm.service.user.LoginLogService;
import com.eghm.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/api")
public class UserController {

    private TokenService tokenService;

    private LoginLogService loginLogService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
    @ApiOperation("查询用户上次登陆的时间和设备")
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

    /**
     * 绑定邮箱发送邮箱验证码
     */
    @PostMapping("/user/send_bind_email")
    @ApiOperation("绑定邮箱发送验证码请求")
    public RespBody<Object> sendBindEmail(SendAuthCodeRequest request) {
        request.setUserId(RequestThreadLocal.getUserId());
        userService.toBindEmail(request);
        return RespBody.success();
    }

    /**
     * 绑定邮箱 目前绑定邮箱不需要短信二次校验,后期可以改为先短信校验,再邮箱校验
     */
    @PostMapping("/user/bind_email")
    @ApiOperation("首次绑定邮箱")
    public RespBody<Object> bindEmail(BindEmailRequest request) {
        request.setUserId(RequestThreadLocal.getUserId());
        userService.bindEmail(request);
        return RespBody.success();
    }


}
