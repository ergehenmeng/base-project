package com.eghm.web.controller;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.constants.ConfigConstant;
import com.eghm.model.dto.business.merchant.MerchantLoginRequest;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.login.LoginRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.service.business.MerchantService;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.IpUtil;
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

    private final SysOperatorService sysOperatorService;

    private final SysConfigApi sysConfigApi;

    private final MerchantService merchantService;

    @PostMapping("/login")
    @ApiOperation("管理后台登陆")
    public RespBody<LoginResponse> login(@Validated @RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        String key = IpUtil.getIpAddress(servletRequest);
        if (this.verifyCodeError(key, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = sysOperatorService.login(request.getUserName(), request.getPwd());
        return RespBody.success(response);
    }

    @PostMapping("/merchant/login")
    @ApiOperation("商户端登陆")
    public RespBody<LoginResponse> login(@Validated @RequestBody MerchantLoginRequest request, HttpServletRequest servletRequest) {
        String key = IpUtil.getIpAddress(servletRequest);
        if (this.verifyCodeError(key, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = merchantService.login(request.getUserName(), request.getPwd());
        return RespBody.success(response);
    }

    /**
     * 校验验证码
     *
     * @param key  缓存key
     * @param code 用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCodeError(String key, String code) {
        int env = sysConfigApi.getInt(ConfigConstant.ENV, CommonConstant.ENV_PROD);
        // 开发环境默认不校验验证码
        if (env == CommonConstant.ENV_DEV) {
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
