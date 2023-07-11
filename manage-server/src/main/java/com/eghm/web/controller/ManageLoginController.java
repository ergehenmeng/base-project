package com.eghm.web.controller;

import com.eghm.configuration.SystemProperties;
import com.eghm.configuration.annotation.SkipPerm;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.ext.JwtUser;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.login.LoginRequest;
import com.eghm.enums.Env;
import com.eghm.enums.ErrorCode;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.login.LoginResponse;
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
 * @author 二哥很猛
 * @date 2022/1/28 17:01
 */
@RestController
@Api(tags = "登陆")
@AllArgsConstructor
@RequestMapping("/manage")
public class ManageLoginController {

    private final CacheService cacheService;

    private final SysUserService sysUserService;

    private final SystemProperties systemProperties;

    @PostMapping("/login")
    @ApiOperation("管理后台登陆")
    public RespBody<LoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        String key = IpUtil.getIpAddress(servletRequest);
        if (this.verifyCodeError(key, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = sysUserService.login(request.getUserName(), request.getPwd());
        return RespBody.success(response);
    }

    @PostMapping("/exit")
    @ApiOperation("退出登录")
    @SkipPerm
    public RespBody<Void> exit() {
        JwtUser user = SecurityHolder.getUser();
        if (user != null) {
            // 删除锁屏状态
            cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
        }
        return RespBody.success();
    }

    /**
     * 校验验证码
     *
     * @param key  缓存key
     * @param code 用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCodeError(String key, String code) {
        Env env = systemProperties.getEnv();
        // 开发环境默认不校验验证码
        if (env == Env.DEV || env == Env.TEST) {
            return false;
        }
        String redisKey = CacheConstant.IMAGE_CAPTCHA + key;
        String value = cacheService.getValue(redisKey);
        if (value == null) {
            return true;
        }
        cacheService.delete(redisKey);
        return !value.equalsIgnoreCase(code);
    }

}
