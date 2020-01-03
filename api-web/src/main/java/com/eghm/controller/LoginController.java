package com.eghm.controller;

import com.eghm.annotation.SkipAccess;
import com.eghm.model.dto.login.AccountLoginRequest;
import com.eghm.model.dto.login.LoginSendSmsRequest;
import com.eghm.model.dto.login.SmsLoginRequest;
import com.eghm.model.ext.RespBody;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.service.user.UserService;
import com.eghm.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆,找回密码相关
 *
 * @author 二哥很猛
 * @date 2019/8/20 10:17
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登陆,找回密码功能")
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;

    /**
     * 发送登陆验证码❶
     */
    @ApiOperation("发送登陆验证码")
    @PostMapping("/login/send_sms")
    public RespBody sendSms(LoginSendSmsRequest request) {
        userService.loginSendSms(request.getMobile());
        return RespBody.getInstance();
    }

    /**
     * 短信验证码登陆❷
     */
    @ApiOperation("短信验证码登陆")
    @PostMapping("/login/by_sms")
    public RespBody<LoginTokenVO> bySms(SmsLoginRequest login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(userService.smsLogin(login));
    }

    /**
     * 邮箱或手机号密码登陆
     */
    @ApiOperation("手机或邮箱密码登陆")
    @PostMapping("/login/by_account")
    @SkipAccess
    public RespBody<LoginTokenVO> byAccount(AccountLoginRequest login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(userService.accountLogin(login));
    }
}
