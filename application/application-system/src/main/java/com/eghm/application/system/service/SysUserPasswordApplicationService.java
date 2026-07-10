package com.eghm.application.system.service;

import cn.hutool.crypto.SecureUtil;
import com.eghm.application.shared.configuration.encoder.Encoder;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysUser;
import com.eghm.domain.system.repository.SysUserRepository;
import com.eghm.application.shared.dto.sys.user.PasswordEditRequest;
import com.eghm.application.shared.manager.LoginCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 系统用户密码服务 - 负责密码修改、重置、校验
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Service
@RequiredArgsConstructor
public class SysUserPasswordApplicationService {

    private final Encoder encoder;
    private final SysUserRepository sysUserRepository;
    private final LoginCacheManager loginCacheManager;
    private final SysUserCommandApplicationService sysUserCommandService;

    public void updateLoginPassword(PasswordEditRequest request) {
        SysUser user = sysUserCommandService.getByIdRequired(request.getUserId());
        this.checkPassword(SecureUtil.sha256(request.getOldPwd()), user.getPwd());
        String newPassword = encoder.encode(SecureUtil.sha256(request.getNewPwd()));
        user.changePassword(newPassword, LocalDateTime.now());
        sysUserRepository.updatePassword(user.getId(), user.getPwd(), user.getPwdUpdateTime());
    }

    public void resetPassword(Long id) {
        SysUser user = sysUserCommandService.getByIdRequired(id);
        String password = this.initPassword(user.getMobile());
        user.resetPassword(password, LocalDateTime.now());
        sysUserRepository.resetPassword(user.getId(), user.getPwd(), user.getPwdUpdateTime());
        loginCacheManager.clearLoginLockCache(user.getUserName(), user.getMobile());
    }

    public void checkPassword(Long userId, String rawPassword) {
        SysUser user = sysUserCommandService.getByIdRequired(userId);
        boolean match = encoder.match(rawPassword, user.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
    }

    private String initPassword(String mobile) {
        String rsaPassword = SecureUtil.sha256(mobile.substring(3));
        return encoder.encode(rsaPassword);
    }

    private void checkPassword(String rawPassword, String targetPassword) {
        boolean match = encoder.match(rawPassword, targetPassword);
        if (!match) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }
    }
}
