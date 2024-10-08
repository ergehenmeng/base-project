package com.eghm.web.controller;

import com.eghm.cache.CacheService;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.SystemProperties;
import com.eghm.annotation.SkipPerm;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.login.LoginRequest;
import com.eghm.enums.Env;
import com.eghm.enums.ErrorCode;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.eghm.utils.CacheUtil.CAPTCHA_CACHE;

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

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("管理后台登陆")
    public RespBody<LoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        if (this.verifyCodeError(IpUtil.getIpAddress(servletRequest), request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = sysUserService.login(request.getUserName(), request.getPwd());
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

    /**
     * 校验验证码
     *
     * @param key  缓存key ip地址
     * @param code 用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCodeError(String key, String code) {
        Env env = systemProperties.getEnv();
        // 开发环境默认不校验验证码
        if (env == Env.DEV || env == Env.TEST) {
            return false;
        }
        String value = CAPTCHA_CACHE.getIfPresent(key);
        if (value == null) {
            return true;
        }
        CAPTCHA_CACHE.invalidate(key);
        return !code.equals(value);
    }

}
