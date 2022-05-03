package com.eghm.web.controller;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.login.LoginRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyb
 * @date 2022/1/28 17:01
 */
@RestController
@Api(tags = "登陆")
@AllArgsConstructor
@RequestMapping("/manage")
public class ManageLoginController {

    private final CacheService cacheService;

    private final SysOperatorService sysOperatorService;

    @PostMapping("/login")
    @ApiOperation("管理后台登陆")
    public RespBody<LoginResponse> login(HttpServletRequest servletRequest, @Validated LoginRequest request) {
        String key = IpUtil.getIpAddress(servletRequest);
        if (!this.verifyCode(key, request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = sysOperatorService.login(request.getUserName(), request.getPwd());
        return RespBody.success(response);
    }

    /**
     * 校验验证码
     * @param key 缓存key
     * @param code 用户输入的验证码
     * @return true:通过
     */
    private boolean verifyCode(String key, String code) {
        String value = cacheService.getValue(CacheConstant.IMAGE_CAPTCHA + key);
        return value != null && value.equalsIgnoreCase(code);
    }

}
