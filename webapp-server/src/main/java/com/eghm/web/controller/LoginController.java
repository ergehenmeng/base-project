package com.eghm.web.controller;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.MemberTokenService;
import com.eghm.common.SmsService;
import com.eghm.constants.AppHeader;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.login.*;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.TemplateType;
import com.eghm.service.member.MemberService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆,找回密码相关
 *
 * @author 二哥很猛
 * @since 2019/8/20 10:17
 */
@Slf4j
@RestController
@Api(tags = "登陆、密码功能")
@AllArgsConstructor
@RequestMapping(value = "/webapp", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final SmsService smsService;

    private final MemberService memberService;

    private final MemberTokenService memberTokenService;

    @ApiOperation("发送登陆验证码①")
    @PostMapping(value = "/login/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> loginSendSms(@RequestBody @Validated SendSmsDTO dto, HttpServletRequest request) {
        memberService.sendLoginSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @ApiOperation("短信验证码登陆②")
    @PostMapping(value = "/login/mobile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<LoginTokenVO> mobile(@RequestBody @Validated SmsLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(memberService.smsLogin(login));
    }

    @ApiOperation("手机或邮箱密码登陆③")
    @PostMapping(value = "/login/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<LoginTokenVO> account(@RequestBody @Validated AccountLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        login.setSerialNumber(ApiHolder.get().getSerialNumber());
        return RespBody.success(memberService.accountLogin(login));
    }

    @ApiOperation("忘记密码发送验证码①")
    @PostMapping(value = "/forget/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> forgetSendSms(@RequestBody @Validated SendSmsDTO dto, HttpServletRequest request) {
        memberService.sendForgetSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @ApiOperation("忘记密码验证短信验证码②")
    @PostMapping(value = "/forget/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<String> verify(@RequestBody @Validated VerifySmsDTO request) {
        String requestId = smsService.verifySmsCode(TemplateType.FORGET, request.getMobile(), request.getSmsCode());
        return RespBody.success(requestId);
    }

    @ApiOperation("忘记密码设置新密码③")
    @PostMapping(value = "/forget/setPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> setPwd(@RequestBody @Validated SetPasswordDTO request) {
        boolean flag = smsService.verifyRequestId(request.getRequestId());
        if (!flag) {
            return RespBody.error(ErrorCode.REQUEST_ID_EXPIRE);
        }
        memberService.setPassword(request.getRequestId(), request.getPassword());
        return RespBody.success();
    }

    @ApiOperation("刷新token")
    @PostMapping(value = "/token/refresh")
    public RespBody<String> refresh(@RequestHeader(value = AppHeader.REFRESH_TOKEN, required = false) String refreshToken) {
        if (StrUtil.isBlank(refreshToken)) {
            log.warn("请求头没有包含Refresh-Token,无法刷新");
            return RespBody.error(ErrorCode.REFRESH_TOKEN_EXPIRE);
        }
        String token = memberTokenService.refreshToken(refreshToken);
        return RespBody.success(token);
    }
}
