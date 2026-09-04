package com.eghm.app.manage.controller;

import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.annotation.RateLimiter;
import com.eghm.foundation.core.annotation.SkipPerm;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.enums.Env;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.enums.LoginType;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.foundation.web.utility.CacheUtil;
import com.eghm.foundation.web.utility.IpUtil;
import com.eghm.platform.iam.dto.LoginRequest;
import com.eghm.platform.iam.dto.SmsLoginRequest;
import com.eghm.platform.iam.dto.SmsVerifyRequest;
import com.eghm.platform.iam.dto.TotpBindRequest;
import com.eghm.platform.iam.dto.TotpCheckRequest;
import com.eghm.platform.iam.service.SysUserService;
import com.eghm.platform.iam.service.UserTokenService;
import com.eghm.platform.iam.vo.LoginMenuResponse;
import com.eghm.platform.iam.vo.LoginResponse;
import com.eghm.platform.iam.vo.TotpLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:01
 */
@RestController
@Tag(name = "登陆")
@AllArgsConstructor
@RequestMapping(value = "/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final CacheService cacheService;

    private final SysUserService sysUserService;

    private final UserTokenService userTokenService;
    
    private final ApplicationProperties applicationProperties;

    /**
     * 账号密码登录时,如果未开启双因子验证,则直接登录成功, 如开启双因子验证, 在第一次登录后需绑定双因子, 后续登录需要输入双因子验证码才可登录
     */
    @RateLimiter(value = "login", limit = 20, scope = RateLimiter.Scope.IP)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "管理后台登陆❶")
    public RespBody<TotpLoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.PASSWORD, ErrorCode.PWD_NOT_SUPPORTED);
        String openId = (String) servletRequest.getSession().getAttribute(CommonConstant.OPEN_ID);
        TotpLoginResponse response = sysUserService.login(request.getUserName(), request.getPwd(), openId, IpUtil.getIpAddress(servletRequest));
        return RespBody.success(response);
    }

    @PostMapping(value = "/checkTotp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "校验双因子❷")
    public RespBody<LoginResponse> checkTotp(@Validated @RequestBody TotpCheckRequest request, HttpServletRequest servletRequest) {
        LoginResponse response = sysUserService.checkTotp(request, IpUtil.getIpAddress(servletRequest));
        return RespBody.success(response);
    }

    @PostMapping(value = "/bindTotp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "绑定双因子验证❷")
    public RespBody<LoginResponse> bindTotp(@Validated @RequestBody TotpBindRequest request, HttpServletRequest servletRequest) {
        LoginResponse response = sysUserService.bindTotp(request, IpUtil.getIpAddress(servletRequest));
        return RespBody.success(response);
    }

    @SkipPerm
    @GetMapping("/permission")
    @Operation(summary = "获取登录菜单")
    public RespBody<LoginMenuResponse> getPermission() {
        LoginMenuResponse response = sysUserService.getPermission();
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
            CacheUtil.PERMISSION_CACHE.invalidate(user.getToken());
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
    @Operation(summary = "发送登陆验证码①")
    public RespBody<LoginResponse> sendSms(@Validated @RequestBody SmsVerifyRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        sysUserService.sendLoginSms(request.getMobile(), IpUtil.getIpAddress(servletRequest));
        return RespBody.success();
    }

    @PostMapping(value = "/smsLogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "验证码登录②")
    public RespBody<LoginResponse> smsLogin(@Validated @RequestBody SmsLoginRequest request, HttpServletRequest servletRequest) {
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        String openId = (String) servletRequest.getSession().getAttribute(CommonConstant.OPEN_ID);
        LoginResponse response = sysUserService.smsLogin(request, IpUtil.getIpAddress(servletRequest), openId);
        return RespBody.success(response);
    }

    /**
     * 校验登录方式是否开启
     *
     * @param type      登录方式
     * @param errorCode 错误时报错
     */
    private void checkLoginType(LoginType type, ErrorCode errorCode) {
        List<LoginType> typeList = applicationProperties.getManage().getLoginTypes();
        if (!typeList.contains(type)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 校验验证码
     *
     * @param servletRequest request
     * @param code           用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCodeError(HttpServletRequest servletRequest, String code) {
        Env env = applicationProperties.getEnv();
        // 开发环境默认不校验验证码
        if (env == Env.DEV || env == Env.TEST) {
            return false;
        }
        String sessionId = servletRequest.getSession().getId();
        String captchaKey = CacheConstant.CAPTCHA_KEY_PREFIX + sessionId;
        String captchaValue = cacheService.getValue(captchaKey);
        // 验证码使用后即为无效
        cacheService.delete(captchaKey);
        return !code.equalsIgnoreCase(captchaValue);
    }

}