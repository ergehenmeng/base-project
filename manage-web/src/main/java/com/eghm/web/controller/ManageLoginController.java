package com.eghm.web.controller;

import com.eghm.common.enums.ErrorCode;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.login.LoginRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.SysOperatorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb
 * @date 2022/1/28 17:01
 */
@RestController
@RequestMapping("/manage")
public class ManageLoginController {

    private CacheService cacheService;

    private SysOperatorService sysOperatorService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setSysOperatorService(SysOperatorService sysOperatorService) {
        this.sysOperatorService = sysOperatorService;
    }

    @PostMapping("/login")
    @ApiOperation("管理后台登陆")
    public RespBody<LoginResponse> login(@Validated LoginRequest request) {
        if (!this.verifyCode(request.getKey(), request.getVerifyCode())) {
            return RespBody.error(ErrorCode.IMAGE_CODE_ERROR);
        }
        LoginResponse response = sysOperatorService.login(request.getUserName(), request.getPwd());
        return RespBody.success(response);
    }

    private boolean verifyCode(String key, String code) {
        String value = cacheService.getValue(key);
        return value != null && value.equalsIgnoreCase(code);
    }
}
