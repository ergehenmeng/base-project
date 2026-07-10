package com.eghm.interfaces.webapp.controller;

import com.eghm.application.shared.common.MemberTokenService;
import com.eghm.application.shared.common.SmsService;
import com.eghm.constants.ApplicationHeader;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.sys.login.*;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.application.member.service.MemberApplicationService;
import com.eghm.interfaces.core.utils.IpUtil;
import com.eghm.application.shared.vo.login.LoginTokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * 登陆,找回密码相关
 *
 * @author 二哥很猛
 * @since 2019/8/20 10:17
 */
@Slf4j
@RestController
@Tag(name = "登陆、密码功能")
@AllArgsConstructor
@RequestMapping(value = "/webapp", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final SmsService smsService;

    private final MemberApplicationService memberService;

    private final MemberTokenService memberTokenService;

    @Operation(summary = "发送登陆验证码①")
    @PostMapping(value = "/login/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> loginSendSms(@RequestBody @Validated SendSmsDTO dto, HttpServletRequest request) {
        memberService.sendLoginSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @Operation(summary = "短信验证码登陆②")
    @PostMapping(value = "/login/mobile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<LoginTokenVO> mobile(@RequestBody @Validated SmsLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(memberService.smsLogin(login));
    }

    @Operation(summary = "手机或邮箱密码登陆❶")
    @PostMapping(value = "/login/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<LoginTokenVO> account(@RequestBody @Validated AccountLoginDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        login.setSerialNumber(ApiHolder.get().getSerialNumber());
        return RespBody.success(memberService.accountLogin(login));
    }

    @Operation(summary = "验证码二次校验❷")
    @PostMapping(value = "/login/double/check", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<LoginTokenVO> doubleCheck(@RequestBody @Validated DoubleCheckDTO login, HttpServletRequest request) {
        login.setIp(IpUtil.getIpAddress(request));
        return RespBody.success(memberService.doubleCheck(login));
    }

    @Operation(summary = "忘记密码发送验证码①")
    @PostMapping(value = "/forget/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> forgetSendSms(@RequestBody @Validated SendSmsDTO dto, HttpServletRequest request) {
        memberService.sendForgetSms(dto.getMobile(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @Operation(summary = "忘记密码验证短信验证码②")
    @PostMapping(value = "/forget/verify", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<String> verify(@RequestBody @Validated VerifySmsDTO request) {
        String requestId = smsService.verifySmsCode(TemplateType.FORGET, request.getMobile(), request.getSmsCode());
        return RespBody.success(requestId);
    }

    @Operation(summary = "忘记密码设置新密码③")
    @PostMapping(value = "/forget/setPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> setPwd(@RequestBody @Validated SetPasswordDTO request) {
        boolean flag = smsService.verifyRequestId(request.getRequestId());
        if (!flag) {
            return RespBody.error(ErrorCode.REQUEST_ID_EXPIRE);
        }
        memberService.setPassword(request.getRequestId(), request.getPassword());
        return RespBody.success();
    }

    @Operation(summary = "刷新token")
    @PostMapping(value = "/token/refresh")
    public RespBody<String> refresh(@RequestHeader(value = ApplicationHeader.REFRESH_TOKEN, required = false) String refreshToken) {
        if (isBlank(refreshToken)) {
            log.warn("请求头没有包含Refresh-Token,无法刷新");
            return RespBody.error(ErrorCode.REFRESH_TOKEN_EXPIRE);
        }
        String token = memberTokenService.refreshToken(refreshToken);
        return RespBody.success(token);
    }
}
