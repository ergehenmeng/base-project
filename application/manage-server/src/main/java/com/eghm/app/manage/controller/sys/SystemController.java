package com.eghm.app.manage.controller.sys;

import cn.hutool.crypto.SecureUtil;
import com.eghm.foundation.core.annotation.SkipPerm;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.dto.ext.RespBody;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.platform.iam.dto.CheckPwdRequest;
import com.eghm.platform.iam.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb-eghm
 * @since 2026/5/29
 */
@RestController
@AllArgsConstructor
@Tag(name="系统设置")
@RequestMapping(value = "/manage/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {
    
    private final CacheService cacheService;
    
    private final SysUserService sysUserService;
    
    @PostMapping("/lock/screen")
    @Operation(summary = "锁屏")
    @SkipPerm
    public RespBody<Void> lockScreen() {
        UserToken user = SecurityHolder.getUserRequired();
        cacheService.setValue(CacheConstant.LOCK_SCREEN + user.getId(), true, CommonConstant.MAX_LOCK_SCREEN);
        return RespBody.success();
    }
    
    @PostMapping(value = "/unlock/screen", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "解锁屏幕")
    @SkipPerm
    public RespBody<Void> unlockScreen(@RequestBody @Validated CheckPwdRequest request) {
        Long userId = SecurityHolder.getUserId();
        sysUserService.checkPassword(userId, SecureUtil.sha256(request.getPwd()));
        cacheService.delete(CacheConstant.LOCK_SCREEN + userId);
        return RespBody.success();
    }
}
