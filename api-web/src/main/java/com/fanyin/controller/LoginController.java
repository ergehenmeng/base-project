package com.fanyin.controller;

import com.fanyin.common.constant.SmsTypeConstant;
import com.fanyin.model.dto.login.AccountLoginRequest;
import com.fanyin.model.dto.login.SendSmsCodeRequest;
import com.fanyin.model.dto.login.SmsLoginRequest;
import com.fanyin.model.ext.AccessToken;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.SmsService;
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
    private SmsService smsService;

    @Autowired
    private UserService userService;

    /**
     * 发送登陆验证码
     */
    @ApiOperation("发送登陆验证码")
    @PostMapping("/login/send_sms")
    public RespBody sendSms(SendSmsCodeRequest code){
        smsService.sendSmsCode(SmsTypeConstant.LOGIN_SMS,code.getMobile());
        return RespBody.getInstance();
    }

    /**
     * 短信验证码登陆
     */
    @ApiOperation("短信验证码登陆")
    @PostMapping("/login/by_sms")
    public RespBody<AccessToken> bySms(SmsLoginRequest login){
        return RespBody.success(userService.smsLogin(login));
    }

    /**
     * 邮箱或手机号密码登陆
     */
    @ApiOperation("手机或邮箱密码登陆")
    @PostMapping("/login/by_account")
    public RespBody<AccessToken> byAccount(AccountLoginRequest login){
        return RespBody.success(userService.accountLogin(login));
    }
}
