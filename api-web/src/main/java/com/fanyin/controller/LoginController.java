package com.fanyin.controller;

import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.login.LoginSendSmsRequest;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.ext.RespBody;
import com.fanyin.model.vo.login.LoginToken;
import com.fanyin.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆,找回密码相关
 * @author 二哥很猛
 * @date 2019/8/20 10:17
 */
@RestController
@Api(tags = "登陆,找回密码功能")
public class LoginController extends AbstractController{

    @Autowired
    private UserService userService;

    /**
     * 发送登陆验证码❶
     */
    @ApiOperation("发送登陆验证码")
    @PostMapping("/login/send_sms")
    public RespBody sendSms(LoginSendSmsRequest request){
        userService.loginSendSms(request.getMobile());
        return RespBody.getInstance();
    }

    /**
     * 短信验证码登陆❷
     */
    @ApiOperation("短信验证码登陆")
    @PostMapping("/login/by_sms")
    public RespBody<LoginToken> bySms(SmsLoginRequest login){
        return RespBody.success(userService.smsLogin(login));
    }

    /**
     * 邮箱或手机号密码登陆
     */
    @ApiOperation("手机或邮箱密码登陆")
    @PostMapping("/login/by_account")
    public RespBody<LoginToken> byAccount(AccountLoginRequest login){
        return RespBody.success(userService.accountLogin(login));
    }
}
