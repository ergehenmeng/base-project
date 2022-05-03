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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 登陆,找回密码相关
 *
 * @author 二哥很猛
 * @date 2019/8/20 10:17
 */
@RestController
@Api(tags = "登陆、密码功能")
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    private final SmsService smsService;

    /**
     * 发送登陆验证码(1)
     */
    @ApiOperation("发送登陆验证码⑴")
    @PostMapping("/login/sendSms")
    @SkipAccess
    public RespBody<Void> loginSendSms(@RequestBody @Valid SendSmsDTO request) {
        userService.sendLoginSms(request.getMobile());
        return RespBody.success();
    }

    /**
     * 短信验证码登陆(2)
     */
    @ApiOperation("短信验证码登陆⑵")
    @PostMapping("/login/mobile")
    @SkipAccess
    public RespBody<LoginTokenVO> mobile(@RequestBody @Valid SmsLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(userService.smsLogin(login));
    }

    /**
     * 邮箱或手机号密码登陆
     */
    @ApiOperation("手机或邮箱密码登陆⑶")
    @PostMapping("/login/account")
    @SkipAccess
    public RespBody<LoginTokenVO> account(@RequestBody @Valid AccountLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        login.setSerialNumber(ApiHolder.get().getSerialNumber());
        return RespBody.success(userService.accountLogin(login));
    }


    /**
     * 忘记密码发送验证码①
     */
    @ApiOperation("忘记密码发送验证码①")
    @PostMapping("/forget/sendSms")
    @SkipAccess
    public RespBody<Void> forgetSendSms(@RequestBody @Valid SendSmsDTO request) {
        userService.sendForgetSms(request.getMobile());
        return RespBody.success();
    }

    /**
     * 忘记密码验证短信验证码②
     */
    @ApiOperation("忘记密码验证短信验证码②")
    @PostMapping("/forget/verify")
    @SkipAccess
    public RespBody<String> verify(@RequestBody @Valid VerifySmsDTO request) {
        String requestId = smsService.verifySmsCode(SmsType.FORGET, request.getMobile(), request.getSmsCode());
        return RespBody.success(requestId);
    }

    /**
     * 忘记密码设置新密码③
     */
    @ApiOperation("忘记密码设置新密码③")
    @PostMapping("/forget/setPwd")
    @SkipAccess
    public RespBody<Void> setPwd(@RequestBody @Valid SetPasswordDTO request) {
        Long userId = ApiHolder.getUserId();
        boolean flag = smsService.verifyRequestId(request.getRequestId());
        if (!flag) {
            return RespBody.error(ErrorCode.REQUEST_ID_EXPIRE);
        }
        userService.setPassword(userId, request.getPassword());
        return RespBody.success();
    }
}
