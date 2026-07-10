package com.eghm.domain.system.service;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.repository.SysUserRepository;

/**
 * 系统用户领域服务.
 *
 * @author 二哥很猛
 */
public class SysUserDomainService {

    public void assertUserNameAvailable(SysUserRepository repository, String userName, Long excludeId) {
        if (repository.existsUserName(userName, excludeId)) {
            throw new BusinessException(ErrorCode.USER_NAME_REDO);
        }
    }

    public void assertMobileAvailable(SysUserRepository repository, String mobile, Long excludeId) {
        if (repository.existsMobile(mobile, excludeId)) {
            throw new BusinessException(ErrorCode.MOBILE_REDO);
        }
    }
}
