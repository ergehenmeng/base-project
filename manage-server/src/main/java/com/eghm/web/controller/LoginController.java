package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.cache.CacheService;
import com.eghm.common.UserTokenService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.SecurityHolder;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.sys.login.AuthSmsRequest;
import com.eghm.dto.sys.login.LoginRequest;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.login.SmsVerifyRequest;
import com.eghm.enums.Env;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.LoginType;
import com.eghm.exception.BusinessException;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.AuthPwdResponse;
import com.eghm.vo.login.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:01
 */
@RestController
@Tag(name= "登陆")
@AllArgsConstructor
@RequestMapping(value = "/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final CacheService cacheService;

    private final SysConfigApi sysConfigApi;

    private final SysUserService sysUserService;

    private final SystemProperties systemProperties;

    private final UserTokenService userTokenService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "管理后台登陆")
    public RespBody<LoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.PASSWORD, ErrorCode.PWD_NOT_SUPPORTED);
        String openId = (String) servletRequest.getSession().getAttribute(CommonConstant.OPEN_ID);
        LoginResponse response = sysUserService.login(request.getUserName(), request.getPwd(), openId);
        return RespBody.success(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    @SkipPerm
    public RespBody<Void> logout() {
        UserToken user = SecurityHolder.getUser();
        if (user != null) {
            // 删除锁屏状态
            cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
            userTokenService.logout(user.getToken());
        }
        return RespBody.success();
    }

    @PostMapping(value = "/unbindWeChat", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "解绑微信")
    @SkipPerm
    public RespBody<Void> unbindWeChat() {
        sysUserService.unbindWeChat();
        return RespBody.success();
    }

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送登陆验证码")
    public RespBody<LoginResponse> sendSms(@Validated @RequestBody SmsVerifyRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        sysUserService.sendLoginSms(request.getMobile(), IpUtil.getIpAddress(servletRequest));
        return RespBody.success();
    }

    @PostMapping(value = "/smsLogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "验证码登录")
    public RespBody<LoginResponse> smsLogin(@Validated @RequestBody SmsLoginRequest request, HttpSession session) {
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        String openId = (String) session.getAttribute(CommonConstant.OPEN_ID);
        LoginResponse response = sysUserService.smsLogin(request, openId);
        return RespBody.success(response);
    }

    @PostMapping(value = "/authPwd", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "密码+验证码登录(1)")
    public RespBody<AuthPwdResponse> authPwd(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.PASSWORD_SMS, ErrorCode.SMS_NOT_SUPPORTED);
        AuthPwdResponse response = sysUserService.authPwd(request.getUserName(), request.getPwd(), IpUtil.getIpAddress(servletRequest));
        return RespBody.success(response);
    }

    @PostMapping(value = "/authSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "密码+验证码登录(2)")
    public RespBody<LoginResponse> authSms(@Validated @RequestBody AuthSmsRequest request, HttpSession session) {
        this.checkLoginType(LoginType.PASSWORD_SMS, ErrorCode.SMS_NOT_SUPPORTED);
        String openId = (String) session.getAttribute(CommonConstant.OPEN_ID);
        LoginResponse response = sysUserService.authSms(request, openId);
        return RespBody.success(response);
    }

    /**
     * 校验登录方式是否开启
     *
     * @param type 登录方式
     * @param errorCode 错误时报错
     */
    private void checkLoginType(LoginType type, ErrorCode errorCode) {
        int loginType = sysConfigApi.getInt(ConfigConstant.LOGIN_TYPE);
        if ((loginType & type.getValue()) != type.getValue()) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验验证码
     *
     * @param servletRequest request
     * @param code 用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCodeError(HttpServletRequest servletRequest, String code) {
        Env env = systemProperties.getEnv();
        // 开发环境默认不校验验证码
        if (env == Env.DEV || env == Env.TEST) {
            return false;
        }
        Object value = servletRequest.getSession().getAttribute(CommonConstant.CAPTCHA_KEY);
        // 防止验证码多次使用
        servletRequest.getSession().removeAttribute(CommonConstant.CAPTCHA_KEY);
        return value == null || !code.equalsIgnoreCase(value.toString());
    }

}
