package com.eghm.web.controller;

import com.eghm.annotation.SkipPerm;
import com.eghm.cache.CacheService;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.sys.login.*;
import com.eghm.enums.Env;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.LoginType;
import com.eghm.exception.BusinessException;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginMenuResponse;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.login.TotpLoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/1/28 17:01
 */
@RestController
@Api(tags = "登陆")
@AllArgsConstructor
@RequestMapping(value = "/manage", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final CacheService cacheService;

    private final SysUserService sysUserService;

    private final SystemProperties systemProperties;

    private final UserTokenService userTokenService;

    /**
     * 账号密码登录时,如果未开启双因子验证,则直接登录成功, 如开启双因子验证, 在第一次登录后需绑定双因子, 后续登录需要输入双因子验证码才可登录
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("管理后台登陆❶")
    public RespBody<TotpLoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.PASSWORD, ErrorCode.PWD_NOT_SUPPORTED);
        String openId = (String) servletRequest.getSession().getAttribute(CommonConstant.OPEN_ID);
        TotpLoginResponse response = sysUserService.login(request.getUserName(), request.getPwd(), openId);
        return RespBody.success(response);
    }

    @PostMapping(value = "/checkTotp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("校验双因子❷")
    public RespBody<LoginResponse> checkTotp(@Validated @RequestBody TotpCheckRequest request) {
        LoginResponse response = sysUserService.checkTotp(request);
        return RespBody.success(response);
    }

    @PostMapping(value = "/bindTotp", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("绑定双因子验证(未登录绑定)")
    public RespBody<LoginResponse> bindTotp(@Validated @RequestBody TotpBindRequest request) {
        LoginResponse response = sysUserService.bindTotp(request);
        return RespBody.success(response);
    }

    @SkipPerm
    @GetMapping("/permission")
    @ApiOperation("获取登录菜单")
    public RespBody<LoginMenuResponse> getPermission() {
        LoginMenuResponse response = sysUserService.getPermission();
        return RespBody.success(response);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
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
    @ApiOperation("解绑微信")
    @SkipPerm
    public RespBody<Void> unbindWeChat() {
        sysUserService.unbindWeChat();
        return RespBody.success();
    }

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("发送登陆验证码①")
    public RespBody<LoginResponse> sendSms(@Validated @RequestBody SmsVerifyRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(servletRequest, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        sysUserService.sendLoginSms(request.getMobile(), IpUtil.getIpAddress(servletRequest));
        return RespBody.success();
    }

    @PostMapping(value = "/smsLogin", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("验证码登录②")
    public RespBody<LoginResponse> smsLogin(@Validated @RequestBody SmsLoginRequest request, HttpSession session) {
        this.checkLoginType(LoginType.SMS, ErrorCode.SMS_NOT_SUPPORTED);
        String openId = (String) session.getAttribute(CommonConstant.OPEN_ID);
        LoginResponse response = sysUserService.smsLogin(request, openId);
        return RespBody.success(response);
    }

    /**
     * 校验登录方式是否开启
     *
     * @param type 登录方式
     * @param errorCode 错误时报错
     */
    private void checkLoginType(LoginType type, ErrorCode errorCode) {
        List<LoginType> typeList = systemProperties.getManage().getLoginType();
        if (!typeList.contains(type)) {
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
