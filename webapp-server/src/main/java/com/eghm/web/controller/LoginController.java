package com.eghm.web.controller;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.SmsType;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.login.*;
import com.eghm.model.vo.login.LoginTokenVO;
import com.eghm.service.common.SmsService;
import com.eghm.service.user.UserService;
import com.eghm.utils.IpUtil;
import com.eghm.web.annotation.SkipAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "登陆、密码功能")
@AllArgsConstructor
@RequestMapping("/webapp")
public class LoginController {

    private final UserService userService;

    private final SmsService smsService;

    @ApiOperation("发送登陆验证码①")
    @PostMapping("/login/sendSms")
    @SkipAccess
    public RespBody<Void> loginSendSms(@RequestBody @Validated SendSmsDTO request) {
        userService.sendLoginSms(request.getMobile());
        return RespBody.success();
    }

    @ApiOperation("短信验证码登陆②")
    @PostMapping("/login/mobile")
    @SkipAccess
    public RespBody<LoginTokenVO> mobile(@RequestBody @Validated SmsLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(userService.smsLogin(login));
    }

    @ApiOperation("手机或邮箱密码登陆③")
    @PostMapping("/login/account")
    @SkipAccess
    public RespBody<LoginTokenVO> account(@RequestBody @Validated AccountLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        login.setSerialNumber(ApiHolder.get().getSerialNumber());
        return RespBody.success(userService.accountLogin(login));
    }

    @ApiOperation("忘记密码发送验证码①")
    @PostMapping("/forget/sendSms")
    @SkipAccess
    public RespBody<Void> forgetSendSms(@RequestBody @Validated SendSmsDTO request) {
        userService.sendForgetSms(request.getMobile());
        return RespBody.success();
    }

    @ApiOperation("忘记密码验证短信验证码②")
    @PostMapping("/forget/verify")
    @SkipAccess
    public RespBody<String> verify(@RequestBody @Validated VerifySmsDTO request) {
        String requestId = smsService.verifySmsCode(SmsType.FORGET, request.getMobile(), request.getSmsCode());
        return RespBody.success(requestId);
    }

    @ApiOperation("忘记密码设置新密码③")
    @PostMapping("/forget/setPwd")
    @SkipAccess
    public RespBody<Void> setPwd(@RequestBody @Validated SetPasswordDTO request) {
        Long userId = ApiHolder.getUserId();
        boolean flag = smsService.verifyRequestId(request.getRequestId());
        if (!flag) {
            return RespBody.error(ErrorCode.REQUEST_ID_EXPIRE);
        }
        userService.setPassword(userId, request.getPassword());
        return RespBody.success();
    }
}
