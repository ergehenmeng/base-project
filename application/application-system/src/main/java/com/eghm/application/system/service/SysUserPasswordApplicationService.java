package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.user.PasswordEditRequest;

/**
 * 系统用户密码服务 - 负责密码修改、重置、校验
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
public interface SysUserPasswordApplicationService {

    void updateLoginPassword(PasswordEditRequest request);

    void resetPassword(Long id);

    void checkPassword(Long userId, String rawPassword);
}
